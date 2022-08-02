package com.base.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream流式操作工具类
 *
 * @author gjmou
 * @date 2022.06.23
 */
public class StreamUtils {
    /**
     * 处理supplier生产者的异常，并进行返回
     * Supplier<Arith> supplier = tryCatch(() -> Arith.class.newInstance());
     */
    public static <R> Supplier<R> tryCatch(CheckedSupplier<R> checkedSupplier) {
        return () -> {
            try {
                return checkedSupplier.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }


    /**
     * 根据条件获取集合中的第一条数据，如果不存在对应的值则返回一个null值
     *
     * @param collection 查询的集合
     * @param predicate  条件
     * @param <T>
     * @return 过滤后的第一条数据
     */
    public static <T> T filterFirst(Collection<T> collection, Predicate<T> predicate) {
        return filterFirst(collection, predicate, Function.identity(), null);
    }

    /**
     * 根据条件获取集合中的第一条数据，如果不存在对应的值则返回一个默认对象
     *
     * @param collection 查询的集合
     * @param predicate  条件
     * @param supplier   默认对象
     * @param <T>
     * @return 过滤后的第一条数据
     */
    public static <T> T filterFirst(Collection<T> collection, Predicate<T> predicate, Supplier<T> supplier) {
        return filterFirst(collection, predicate, Function.identity(), supplier.get());
    }

    /**
     * 根据条件获取集合中的第一条数据，并根据条件进行映射，如果不存在对应的值则返回一个默认对象
     *
     * @param collection 查询的集合
     * @param predicate  条件
     * @param function   映射函数
     * @param defaultVal 默认值
     * @param <T>
     * @param <R>
     * @return 过滤并映射后的第一条数据
     */
    public static <T, R> R filterFirst(Collection<T> collection, Predicate<T> predicate, Function<T, R> function, R defaultVal) {
        return collection.stream().filter(predicate).map(function).findFirst().orElse(defaultVal);
    }

    /**
     * 获取集合中的第一条数据，并根据条件进行映射，如果不存在对应的值则返回一个默认对象
     *
     * @param collection 查询的集合
     * @param function   映射函数
     * @param defaultVal 默认值
     * @param <T>
     * @param <R>
     * @return 映射后的第一条数据
     */
    public static <T, R> R findFirst(Collection<T> collection, Function<T, R> function, R defaultVal) {
        return collection.stream().findFirst().map(function).orElse(defaultVal);
    }

    /**
     * 获取集合中的第一条数据，如果不存在对应的值则返回一个默认对象
     *
     * @param collection 查询的集合
     * @param supplier   生产者
     * @param <T>
     * @return 集合中的第一条数据
     */
    public static <T> T findFirst(Collection<T> collection, Supplier<T> supplier) {
        return findFirst(collection, Function.identity(), supplier.get());
    }

    /**
     * 获取集合中的第一条数据，如果不存在对应的值则返回一个null值
     *
     * @param collection 查询的集合
     * @param <T>
     * @return 集合中的第一条数据
     */
    public static <T> T findFirst(Collection<T> collection) {
        return findFirst(collection, Function.identity(), null);
    }


    /**
     * 将字符串数组中的空值去除，并返回指定字符串分割的字符串
     *
     * @param charSequence 数组
     * @param datas        分隔符号
     * @return 执行字符分割的字符串
     */
    public static String joining(CharSequence charSequence, String... datas) {
        if (datas == null) return "";
        return joining(Arrays.stream(datas), charSequence);
    }

    /**
     * 将集合中的空值去除，并返回指定字符分割的字符串
     *
     * @param collection   集合
     * @param charSequence 分割符号
     * @return 执行字符分割的字符串
     */
    public static String joining(Collection<? extends String> collection, CharSequence charSequence) {
        return joining(collection.stream(), charSequence);
    }

    /**
     * 将字符串流中的空值去除，并返回指定字符分割的字符串
     *
     * @param stream       stream流
     * @param charSequence 分割符号
     * @return 执行字符分割的字符串
     */
    public static String joining(Stream<? extends String> stream, CharSequence charSequence) {
        return stream.filter(StringUtils::isNotEmpty).collect(Collectors.joining(charSequence));
    }
}
