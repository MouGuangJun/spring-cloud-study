package com.base.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    /**
     * 获取当前日期和时间：格式：yyyy/MM/dd HH:mm:ss
     */
    public static String getRightDateTime() {
        return getRightDateTime("yyyy/MM/dd HH:mm:ss");
    }


    /**
     * 获取指定格式的当前日期和时间
     */
    public static String getRightDateTime(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }

    /**
     * 获取当前日期：格式：yyyy/MM/dd
     */
    public static String getRightDate() {
        return getRightDate("yyyy/MM/dd");
    }

    /**
     * 获取指定格式的当前日期
     */
    public static String getRightDate(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDate.now());
    }
}
