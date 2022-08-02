package com.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * jackson工具类
 */
public class JacksonUtils {

    /**
     * 将对象转换为jackson
     *
     * @param data 对象
     * @param <T>
     * @return json数据
     */
    public static <T> String obj2Jackson(T data) {
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将json转换为对象
     *
     * @param filePath 文件路径
     * @param clazz    java对应的class对象
     * @param <T>
     * @return java对象
     */
    public static <T> T jacksonFile2Obj(String filePath, Class<? extends T> clazz) {
        return jacksonFile2Obj(new File(filePath), clazz);
    }

    /**
     * 将json转换为对象
     *
     * @param file  文件对象
     * @param clazz java对应的class对象
     * @param <T>
     * @return java对象
     */
    public static <T> T jacksonFile2Obj(File file, Class<? extends T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json转换为对象
     *
     * @param jsonStr json字符串
     * @param clazz   java对应的class对象
     * @param <T>
     * @return java对象
     */
    public static <T> T jackson2Obj(String jsonStr, Class<? extends T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("CACHE_INPUT_DATE", LocalDateTime.of(2021, 7, 18, 18, 29, 40));
        Set<String> removeSet = new HashSet<>();
        map.forEach((key, value) -> {
            if (LocalDateTime.of(2021, 7, 18, 18, 30, 39).minusMinutes(1L).isAfter((LocalDateTime) value)) {
                removeSet.add(key);
            }
        });

        removeSet.forEach(map::remove);

        System.out.println(map);

    }
}