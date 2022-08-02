package com.base.model;

import lombok.Data;

/**
 * 返回前端提醒信息
 */
@Data
public class ResultDto {
    // 接口状态（成功还是失败）
    private boolean success;
    // 错误码
    private String code;
    // 提示信息
    private String msg;

    // 默认构造器
    public ResultDto() {

    }

    public ResultDto(boolean success) {
        this(success, null, null);
    }

    public ResultDto(boolean success, String msg) {
        this(success, null, msg);
    }

    public ResultDto(boolean success, String code, String msg) {
        setSuccess(success);
        setCode(code);
        setMsg(msg);
    }


    // 成功
    public static ResultDto success(String msg) {
        return new ResultDto(true, msg);
    }

    // 失败
    public static ResultDto error(String msg) {
        return error(null, msg);
    }

    // 失败
    public static ResultDto error(String code, String msg) {
        return new ResultDto(false, code, msg);
    }
}
