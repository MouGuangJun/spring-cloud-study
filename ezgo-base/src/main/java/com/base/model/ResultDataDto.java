package com.base.model;

import lombok.Data;

import java.util.List;

/**
 * 返回前端结果集
 */
@Data
public class ResultDataDto<T> extends ResultDto {

    // 数据
    private T data;

    // 数据集合
    private List<T> dataList;

    // 数据记录条数
    private long count;

    // 默认构造器
    public ResultDataDto() {
        super();
    }

    public ResultDataDto(boolean success) {
        super(success);
    }

    public ResultDataDto(boolean success, String msg) {
        super(success, msg);
    }

    public ResultDataDto(boolean success, String code, String msg) {
        super(success, code, msg);
    }

    // 成功
    public static <T> ResultDataDto<T> success(T data, String msg) {
        ResultDataDto<T> result = new ResultDataDto<>(true, msg);
        result.setData(data);
        return result;
    }

    // 成功
    public static <T> ResultDataDto<T> success(List<T> dataList, long count, String msg) {
        ResultDataDto<T> result = new ResultDataDto<>(true, msg);
        result.setDataList(dataList);
        result.setCount(count);
        return result;
    }

    /*// 失败
    public static <T> ResultDataDto<T> error(String msg) {
        return error(null, msg);
    }

    // 失败
    public static <T> ResultDataDto<T> error(String code, String msg) {
        return error(code, msg, null);
    }*/

    // 失败
    public static <T> ResultDataDto<T> error(String code, String msg, T data) {
        ResultDataDto<T> result = new ResultDataDto<>(false, code, msg);
        result.setData(data);
        return result;
    }
}
