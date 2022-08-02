package com.base.utils;

import java.util.Collection;

public class CollectionUtils {

    /**
     * 判断数组是否为空（里面所有元素都为空时，也当作空来处理）
     *
     * @param array 目标数组
     * @param <T>
     * @return true -> 非空数组, false -> 空数组
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组是否为空（里面所有元素都为空时，也当作空来处理）
     *
     * @param array 目标数组
     * @param <T>
     * @return true -> 空数组, false -> 非空数组
     */
    public static <T> boolean isEmpty(T[] array) {
        if (array == null || array.length == 0) return true;

        for (T t : array) {
            if (t != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断集合是否为空（里面所有元素都为空时，也当作空来处理）
     *
     * @param collection 目标集合
     * @return true -> 非空集合, false -> 空集合
     */
    public static <T> boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合是否为空（里面所有元素都为空时，也当作空来处理）
     *
     * @param collection 目标集合
     * @return true -> 空集合, false -> 非空集合
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.size() == 0) return true;
        for (Object next : collection) {
            if (next != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断集合的size是否大于给定的值
     *
     * @param collection  集合
     * @param comparedNum 被比较的大小
     * @return true -> 集合的长度大于comparedNum，false集合的长度小于等于comparedNum
     */
    public static boolean sizeMoreThan(Collection<?> collection, int comparedNum) {
        return collection != null && collection.size() > comparedNum;
    }
}
