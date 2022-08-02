package com.base.constant;

/**
 * 工具类常量
 */
public enum UtilsConstant {
    NULL("NULL", "空值"),
    TableName_CodeLibrary("CODE_LIBRARY","码值表");
    // 成员变量
    private String codeNo; // 码值
    private String describe; // 描述

    // 构造方法
    UtilsConstant(String codeNo, String describe) {
        this.codeNo = codeNo;
        this.describe = describe;
    }

    // getter setter方法
    public String getCodeNo() {
        return codeNo;
    }

    public void setCodeNo(String codeNo) {
        this.codeNo = codeNo;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
