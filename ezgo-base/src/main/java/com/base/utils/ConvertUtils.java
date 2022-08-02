package com.base.utils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * java类型转换操作的工具类
 *
 * @author gjmou
 * @date 2022.06.23
 */
public class ConvertUtils {
    /**
     * 将Object对象转换为String字符串（null值不做操作）
     *
     * @param obj 源对象
     * @return String字符串
     */
    public static String obj2StrExcludeNull(Object obj) {
        return obj == null ? null : Objects.toString(obj);
    }

    /**
     * 将Object对象转换为String字符串（null值转换为默认值）
     *
     * @param obj        源对象
     * @param defaultVal 默认值
     * @return String字符串
     */
    public static String obj2StrWithDefault(Object obj, String defaultVal) {
        return Objects.toString(obj, defaultVal);
    }

    /**
     * 将值为null的字符串转换为""
     *
     * @param val 目标值
     * @return 转换后的值
     */
    public static String nullStr2Empty(String val) {
        return nullStr2Def(val, "");
    }

    /**
     * 将值为整数的字符串转换为0
     *
     * @param val 目标值
     * @return 转换后的值
     */
    public static Integer nullInt2Zero(Integer val) {
        return nullInt2Def(val, 0);
    }

    /**
     * 将值为小数的字符串转换为0
     *
     * @param val 目标值
     * @return 转换后的值
     */
    public static Double nullDouble2Zero(Double val) {
        return nullDouble2Def(val, 0d);
    }

    /**
     * 将值为小数的值转换为0
     *
     * @param val 目标值
     * @return 转换后的值
     */
    public static BigDecimal nullBigDecimal2Zero(BigDecimal val) {
        return nullBigDecimal2Def(val, BigDecimal.ZERO);
    }

    /**
     * 将值为null的字符串转换为默认字符串
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static String nullStr2Def(String val, String defVal) {
        return val == null ? defVal : val;
    }

    /**
     * 将值为null的小数转换为默认小数
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static Double nullDouble2Def(Double val, Double defVal) {
        return val == null ? defVal : val;
    }

    /**
     * 将值为null的整数转换为默认整数
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static Integer nullInt2Def(Integer val, Integer defVal) {
        return val == null ? defVal : val;
    }

    /**
     * 将值为null的小数转换为默认小数
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static BigDecimal nullBigDecimal2Def(BigDecimal val, BigDecimal defVal) {
        return val == null ? defVal : val;
    }

    /**
     * 将数值为0的变量的值转换为默认值
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static int zeroInt2Def(int val, int defVal) {
        return val == 0 ? defVal : val;
    }

    /**
     * 将值为0的小数转换为默认小数
     *
     * @param val    变量值
     * @param defVal 默认值
     * @return 转换后的值
     */
    public static Double zeroDouble2Def(double val, double defVal) {
        return val == 0d ? defVal : val;
    }

    /**
     * 将字符串转换为BigDecimal对象
     *
     * @param val 字符串
     * @return BigDecimal对象
     */
    public static BigDecimal str2BigDecimal(String val) {
        return val == null ? null : new BigDecimal(val);
    }

}
