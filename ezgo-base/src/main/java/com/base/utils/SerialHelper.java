package com.base.utils;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.base.entity.SerialRecord;
import com.base.mapper.SerialRecordMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自动获取当前的最大流水号主键
 */
@Component
public class SerialHelper {
    @Autowired
    private SerialRecordMapper serialRecordMapper;

    // 每张表最大缓存多少个流水号
    private static final Long CACHE_SIZE = 3L;

    private List<CacheSerial> cacheSerials;// 缓存的相关的最大流水号信息

    /**
     * 获取当前最大的流水号
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param prefix     前缀
     * @return 最大的流水号
     */
    // 获取流水号时单独开启属于自己的事务，默认隔离级别为可重复读
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public String getCurrentSerial(String tableName, String columnName, String prefix) {
        return prefix + getCurrentSerial(tableName, columnName);
    }

    /**
     * 获取当前最大的流水号
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 最大的流水号
     */
    // 获取流水号时单独开启属于自己的事务，默认隔离级别为可重复读
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public String getCurrentSerial(String tableName, String columnName) {
        // 表明或者字段名为空
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(columnName)) return null;
        // 将表名和字段名全部转换为大写
        tableName = tableName.toUpperCase();
        columnName = columnName.toUpperCase();

        // 从缓存中进行搜索
        Long serialNo = searchFromCache(tableName, columnName);
        if (serialNo != null) return getSerialByNum(serialNo);

        /*LambdaQueryWrapper<SerialRecord> queryWrapper = new LambdaQueryWrapper<>();
        // 设置对应的查询条件
        // queryWrapper.select(SerialRecord::getCurrentSerial);// 只查询当前的最大流水号
        queryWrapper.eq(SerialRecord::getTableName, tableName)
                .eq(SerialRecord::getColumnName, columnName);
        // 可以唯一确定一条数据
        SerialRecord serialRecord = serialRecordMapper.selectOne(queryWrapper);*/
        // 进行锁表的操作，在获取主键期间，不能对表进行修改
        SerialRecord serialRecord = serialRecordMapper.lockRow(tableName, columnName);
        // 第一次进行流水号的查询
        boolean isInsert = false;
        long maxVal;// 最大值
        long currentVal;// 当前值
        if (serialRecord == null) {
            serialRecord = new SerialRecord();
            // 初始化对应的值
            serialRecord.setTableName(tableName);
            serialRecord.setColumnName(columnName);
            maxVal = CACHE_SIZE;// 标记最大的数值
            currentVal = 1L;// 标记当前的数值
            serialRecord.setCurrentSerial(getSerialByNum(CACHE_SIZE));
            isInsert = true;// 标记为插入的状态
        } else {
            String currentSerial = serialRecord.getCurrentSerial();
            // 从第八位开始截取
            currentSerial = currentSerial.substring(8);
            Long oriVal = Long.valueOf(currentSerial);// 原来的值
            maxVal = oriVal + CACHE_SIZE;// 标记最大的数值
            currentVal = oriVal + 1L;// 标记当前的数值
            serialRecord.setCurrentSerial(getSerialByNum(maxVal));
        }

        // 更新数据库的操作
        if (isInsert) {
            serialRecordMapper.insert(serialRecord);
        } else {
            // 由于不支持联合主键，只能使用wrapper进行更新
            LambdaUpdateWrapper<SerialRecord> updateWrapper = new LambdaUpdateWrapper<>();
            // update serial_record set current_serial = ? where table_name = ? and column_name = ?
            updateWrapper.set(SerialRecord::getCurrentSerial, serialRecord.getCurrentSerial())
                    .eq(SerialRecord::getTableName, tableName)
                    .eq(SerialRecord::getColumnName, columnName);
            serialRecordMapper.update(null, updateWrapper);
        }

        // 将当前的信息放入到缓存中
        CacheSerial cacheSerial = new CacheSerial();
        cacheSerial.setTableName(tableName);
        cacheSerial.setColumnName(columnName);
        cacheSerial.setMaxSerial(new AtomicLong(maxVal));
        cacheSerial.setLastSerial(new AtomicLong(currentVal));
        cacheSerial.setStartDateTime(LocalDateTime.now());
        // 放入到缓存的操作
        if (cacheSerials == null) {
            cacheSerials = new ArrayList<>();
            cacheSerials.add(cacheSerial);
        } else {
            cacheSerials.add(cacheSerial);
        }

        return getSerialByNum(currentVal);

    }

    /**
     * 从缓存中搜索对应的最大流水号
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 空 -> 不存在缓存， 非空 -> 查询到的最大流水号
     */
    private Long searchFromCache(String tableName, String columnName) {
        if (cacheSerials == null) return null;
        // 通过时间进行比较
        CacheSerial cacheSerial = cacheSerials.stream().filter(c -> tableName.equals(c.getTableName()) && columnName.equals(c.getColumnName())).findFirst().orElse(null);
        // 缓存中不存在该表和字段对应的信息
        if (cacheSerial == null) return null;
        // C ! A ! S ! 比较并且交换
        for (; ; ) {
            // 获取当前的流水号
            long current = cacheSerial.getLastSerial().get();

            // 从缓存中获取流水号开始......
            LocalDateTime localDateTime = LocalDateTime.now();// 获取当前时间
            // 已经跨天了，缓存失效
            if (!localDateTime.toLocalDate().equals(cacheSerial.getStartDateTime().toLocalDate())) {
                cacheSerials.remove(cacheSerial);// 将当前对象移除缓存
                return null;
            }

            // 当前已经是最大的流水号了
            if (cacheSerial.getLastSerial().get() == cacheSerial.getMaxSerial().get()) {
                cacheSerials.remove(cacheSerial);// 将当前对象移除缓存
                return null;
            }
            // 从缓存中获取流水号结束......

            // 将当前的流水号加一
            long next = current + 1;
            if (cacheSerial.getLastSerial().compareAndSet(current, next)) {
                return next;
            }
        }
    }

    /**
     * 通过数字获取yyyyMMdd + 8位数的固定长度值
     *
     * @param num 数字
     * @return 固定长度值
     */
    private static String getSerialByNum(Long num) {
        return getSerialByNum(DateUtils.getRightDate("yyyyMMdd"), num);
    }

    /**
     * 通过数字获取yyyyMMdd + 8位数的固定长度值
     *
     * @param date 时间
     * @param num  数字
     * @return 固定长度值
     */
    private static String getSerialByNum(String date, Long num) {
        String numStr = String.valueOf(num);// 获取字符串对象
        if (numStr.length() > 8) throw new RuntimeException("流水号长度超长！");
        StringBuilder builder = new StringBuilder(date);
        for (int i = 0; i < 8 - numStr.length(); i++) {
            builder.append("0");
        }

        builder.append(numStr);
        return builder.toString();
    }

    /**
     * 缓存的流水号
     */
    @Data
    private class CacheSerial {
        private String tableName;// 表名
        private String columnName;// 列名
        private AtomicLong lastSerial;// 上一个流水号
        private AtomicLong maxSerial;// 缓存的最大流水号
        private LocalDateTime startDateTime;// 记录最开始生成缓存的时间
    }

    /**
     * 获取当前最大的流水号（不使用数据的方法）
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param prefix     前缀
     * @return 最大的流水号
     */
    public static String getSimpleSerialNo(String tableName, String columnName, String prefix) {
        return prefix + getSimpleSerialNo(tableName, columnName);
    }

    /**
     * 获取当前最大的流水号（不使用数据的方法）
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 最大的流水号
     */
    public static String getSimpleSerialNo(String tableName, String columnName) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(columnName)) {
            return null;
        }

        // 转换为大写
        tableName = tableName.toUpperCase();
        columnName = columnName.toUpperCase();
        // 设置文件路径
        PropUtils.path = "D:/IDEAWorkSpace/payment/payment-base/src/main/resources/serial/serial.properties";
        // 需要匹配的名字
        String name = tableName + "_" + columnName;
        String value = PropUtils.getValue(name);
        String serial;// 新的流水号
        // 不存在对应的key值，进行初始化操作
        String rightDate = DateUtils.getRightDate("yyyyMMdd");
        if (value == null || !value.substring(0, 8).equals(rightDate)) {
            serial = getSerialByNum(rightDate, 1L);
            // 保存的操作
            PropUtils.setValue(name, serial);

            return serial;
        }

        value = value.substring(8);
        Long oldNum = Long.valueOf(value);

        // 获取新的流水号
        serial = getSerialByNum(rightDate, oldNum + 1L);
        // 保存的操作
        PropUtils.setValue(name, serial);

        return serial;
    }

    public static void main(String[] args) {
        System.out.println(getSimpleSerialNo("PRODUCT_INFO", "SERIALNO", "PI"));
    }
}
