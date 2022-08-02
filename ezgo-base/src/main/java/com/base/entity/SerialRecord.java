package com.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author gjmou
 * @since 2022-07-03
 */
@TableName("serial_record")
public class SerialRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    //@TableId("table_name")
    @TableField("table_name")
    private String tableName;

    /**
     * 字段名
     */
    //@TableId("column_name")
    @TableField("column_name")
    private String columnName;

    /**
     * 当前最大的流水号
     */
    @TableField("current_serial")
    private String currentSerial;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCurrentSerial() {
        return currentSerial;
    }

    public void setCurrentSerial(String currentSerial) {
        this.currentSerial = currentSerial;
    }

    @Override
    public String toString() {
        return "SerialRecord{" +
                "tableName=" + tableName +
                ", columnName=" + columnName +
                ", currentSerial=" + currentSerial +
                "}";
    }
}
