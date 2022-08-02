package com.base.mapper;

import com.base.entity.SerialRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author gjmou
 * @since 2022-07-03
 */
@Repository
public interface SerialRecordMapper extends BaseMapper<SerialRecord> {

    // 锁住当前需要进行操作的记录
    @Select("select table_name as tableName, column_name as columnName, current_serial as currentSerial from serial_record where table_name = #{tableName} and column_name = #{columnName} for update")
    SerialRecord lockRow(String tableName, String columnName);
}
