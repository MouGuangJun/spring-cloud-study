package com.base.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.base.constant.UtilsConstant;
import lombok.Data;
import lombok.ToString;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * java反射相关操作的工具类
 */
public class ReflectUtils {

    @Data
    static class A {
        private final String name = "李四";
        private Integer id;
        private Character sex;
    }

    @ToString
    static class B {
        private String Name;
        private int Id;
        private char Sex;
    }

    // copy对象测试案例
    /*public static void main(String[] args) {
        A a = new A();
        //a.setId(null);
        //a.setName("张三");
        a.setSex('男');
        B b = new B();
        copyEntityIgnoreCase(a, b);
        System.out.println(b);
    }*/


    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap(9);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap(9);

    // 基础类型的转换
    static {
        primitiveWrapperTypeMap.put(Boolean.class, Boolean.TYPE);
        primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
        primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
        primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
        primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
        primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
        primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
        primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
        primitiveWrapperTypeMap.put(Void.class, Void.TYPE);
        Iterator<Map.Entry<Class<?>, Class<?>>> iter = primitiveWrapperTypeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Class<?>, Class<?>> entry = iter.next();
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 从bean对象中获取指定属性的值
     *
     * @param bean      bean对象
     * @param fieldName 属性名称
     * @return 属性值
     */
    public static Object getValueFromBean(Object bean, String fieldName) {
        try {
            return getValueFromBean(bean, bean.getClass().getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从bean对象中获取指定属性的值
     *
     * @param bean  bean对象
     * @param field 属性
     * @return 属性值
     */
    public static Object getValueFromBean(Object bean, Field field) {
        return getValueFromBean(bean.getClass(), bean, field);
    }

    /**
     * 从bean对象中获取指定属性的值
     *
     * @param clazz class对象
     * @param bean  bean对象
     * @param field 属性
     * @return 属性值
     */
    public static Object getValueFromBean(Class<?> clazz, Object bean, Field field) {
        Object value = null;
        try {
            PropertyDescriptor descriptor = null;
            try {
                descriptor = new PropertyDescriptor(field.getName(), clazz);
            } catch (IntrospectionException e) {
                // 如果获取该属性的get、set方法失败，直接进行暴力反射
                // if (Modifier.isFinal(field.getModifiers())) return value;
                field.setAccessible(true);
                value = field.get(bean);
            }

            Method readMethod;
            if (descriptor != null && (readMethod = descriptor.getReadMethod()) != null) {
                // 如果get方法是私有的，开启暴力反射
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }

                // 反射获取对应的值
                value = readMethod.invoke(bean);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return value;
    }


    /**
     * 设置value值到bean对象中
     *
     * @param bean      目标对象
     * @param fieldName 属性名称
     * @param value     值
     */
    public static void setValue2Bean(Object bean, String fieldName, Object value) {
        try {
            setValue2Bean(bean, bean.getClass().getDeclaredField(fieldName), value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置value值到bean对象中
     *
     * @param bean  目标对象
     * @param field 属性
     * @param value 值
     */
    public static void setValue2Bean(Object bean, Field field, Object value) {
        setValue2Bean(bean.getClass(), bean, field, value);
    }

    /**
     * 设置value值到bean对象中
     *
     * @param clazz class对象
     * @param bean  目标对象
     * @param field 属性
     * @param value 值
     */
    public static void setValue2Bean(Class<?> clazz, Object bean, Field field, Object value) {
        try {
            if (field.getType().isPrimitive() && Objects.isNull(value)) return;
            PropertyDescriptor descriptor = null;
            try {
                descriptor = new PropertyDescriptor(field.getName(), clazz);
            } catch (IntrospectionException e) {
                // 如果不存在该属性的set方法则不使用set方法，直接暴力反射给field进行赋值
                // 不需要copy修饰符为final的属性
                if (Modifier.isFinal(field.getModifiers())) return;
                field.setAccessible(true);
                field.set(bean, value);
            }

            Method writeMethod;
            if (descriptor != null && (writeMethod = descriptor.getWriteMethod()) != null) {
                // 如果set方法是私有的，开启暴力反射
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                // 反射进行设值
                writeMethod.invoke(bean, value);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 拷贝源集合中的所有元素到目标class里，返回目标class相应的集合
     *
     * @param sources 源集合
     * @param target  目标class
     * @param <T>
     * @param <R>
     * @return 拷贝后的集合
     */
    public static <T, R> List<R> copyEntityIgnoreCase(List<T> sources, Class<? extends R> target) {
        ArrayList<R> targets = new ArrayList<>();
        sources.forEach(c -> {
            try {
                R bean = target.newInstance();
                copyEntityIgnoreCase(c, bean);
                targets.add(bean);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return targets;
    }

    /**
     * 复制源对象到目标class对象中，其中属性是忽略大小写的
     *
     * @param source      源对象
     * @param targetClass 目标class对象
     * @param <T>
     * @return 目标对象
     */
    public static <T> T copyEntityIgnoreCase(Object source, Class<? extends T> targetClass) {
        try {
            // 创建对象
            T target = targetClass.newInstance();
            // 复制属性
            copyEntityIgnoreCase(source, target);
            // 返回创建的对象
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 复制两个对象属性，其中属性是忽略大小写的
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyEntityIgnoreCase(Object source, Object target) {
        copyEntityIgnoreCase(source, target, (String) null);
    }

    /**
     * 复制两个对象属性，其中属性是忽略大小写的（可以设置忽略属性）
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 忽略的属性
     */
    public static void copyEntityIgnoreCase(Object source, Object target, String... ignoreProperties) {
        // 如果源对象和目标对象任意一个为空值都不执行拷贝的操作
        if (Objects.isNull(source) || Objects.isNull(target)) return;
        Field[] sourceFields = getAllFields(source.getClass());
        Field[] targetFields = getAllFields(target.getClass());
        // 如果忽略大小写后存在多个相同的属性，抛出异常
        List<List<String>> sourceRepeatFields = getRepeatFields(sourceFields);
        if (sourceRepeatFields.size() > 0) {
            throw new RuntimeException(String.format("The same field exists after the source object ignores case, please check the fields: %s", sourceRepeatFields.toString()));
        }

        List<List<String>> targetRepeatFields = getRepeatFields(targetFields);
        if (targetRepeatFields.size() > 0) {
            throw new RuntimeException(String.format("The same field exists after the target object ignores case, please check the fields: %s", targetRepeatFields.toString()));
        }

        for (Field field : targetFields) {
            try {
                // 跳过需要忽略的属性
                if (CollectionUtils.isNotEmpty(ignoreProperties) && Arrays.stream(ignoreProperties).anyMatch(c -> field.getName().equals(c))
                        || Modifier.isFinal(field.getModifiers())) {// final属性不拷贝
                    continue;
                }

                Optional<Field> optional = Arrays.stream(sourceFields).filter(c -> c.getName().equalsIgnoreCase(field.getName())).findFirst();
                if (optional.isPresent()) {
                    // 如果两个属性的类型不一样，不进行拷贝操作
                    if (isAssignable(field.getType(), optional.get().getType())) {
                        // 执行复制的操作
                        setValue2Bean(target, field, getValueFromBean(source, optional.get()));
                    }
                }
            } catch (Throwable ex) {
                throw new RuntimeException(
                        String.format("Could not copy property [%s] from source to target!", field.getName()), ex);
            }
        }
    }


    /**
     * 判断lhsType和rhsType是否存在继承关系
     *
     * @param lhsType 父类
     * @param rhsType 子类
     * @return true -> rhsType继承自lhsType， false -> 不存在继承关系
     */
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        if (lhsType == null) throw new IllegalArgumentException("Left-hand side type must not be null!");
        if (rhsType == null) throw new IllegalArgumentException("Right-hand side type must not be null!");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        } else {
            Class resolvedWrapper;
            if (lhsType.isPrimitive()) {
                resolvedWrapper = (Class) primitiveWrapperTypeMap.get(rhsType);
                return lhsType == resolvedWrapper;
            } else {
                resolvedWrapper = (Class) primitiveTypeToWrapperMap.get(rhsType);
                return resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper);
            }
        }
    }

    /**
     * 判断class中所有属性忽略大小写后是否存在相同的属性
     *
     * @param fields 源class
     * @return 所有重复字段分组后的集合
     */
    private static List<List<String>> getRepeatFields(Field[] fields) {
        return Arrays.stream(fields).filter(distinctByKey(Field::getName))
                .map(Field::getName).collect(Collectors.groupingBy(String::toUpperCase))
                .values().stream().filter(c -> c.size() > 1).collect(Collectors.toList());
    }

    /**
     * 根据对象中选定的字段进行去重
     *
     * @param keyExtractor 源Function转换函数
     * @param <R>          泛型返回值
     * @return true -> 不存在重复的值， false -> 存在重复的值
     */
    private static <R> Predicate<? super Field> distinctByKey(Function<? super Field, R> keyExtractor) {
        ConcurrentHashMap<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            Object val = keyExtractor.apply(t);// 防止多次执行Function的内容
            return seen.putIfAbsent(val == null ? UtilsConstant.NULL.getCodeNo() : val, Boolean.TRUE) == null;
        };
    }

    /*private static <T> Predicate<? super T> distinctByKey(Function<? super T, ?> keyExtractor) {
        ConcurrentHashMap<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }*/

    /**
     * 获取class中的所有属性以及其父类的所有属性
     *
     * @param clazz class对象
     * @return class中的所有属性
     */
    private static Field[] getAllFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Class<?> currClass = clazz; currClass != null && currClass != Object.class; currClass = currClass.getSuperclass()) {
            fields.addAll(Arrays.asList(currClass.getDeclaredFields()));
        }
        Field[] retFields = new Field[fields.size()];

        return fields.toArray(retFields);
    }

    /**
     * 暴力反射对象属性
     *
     * @param obj       被反射的对象
     * @param fieldName 属性名称
     * @param value     值
     */
    public static void forceSetValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            // 设置暴力反射
            field.setAccessible(true);
            field.set(obj, value);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * map转换为实体类
     *
     * @param mapVal map集合
     * @param clazz  目标class
     * @param <T>
     * @return 转换后的实体类对象
     */
    public static <T> T map2Obj(Map<String, Object> mapVal, Class<? extends T> clazz) {
        T bean;
        try {
            bean = clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        setMapVal2Bean(mapVal, bean);

        return bean;
    }

    /**
     * 将Map中的值设置到目标对象中
     *
     * @param mapVal 对象对应的值（map的value值类型需要跟bean的保持一致，否则不做拷贝）
     * @param bean   目标对象u
     */
    public static void setMapVal2Bean(Map<String, Object> mapVal, Object bean) {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 找出mapVal中存在于bean中的所有属性
        Arrays.stream(fields).filter(c -> mapVal.keySet().contains(c.getName())).forEach(c -> {
            try {
                Object value = mapVal.get(c.getName());// 获取mapVal中该属性对应的值
                // 如果目标参数类型是基本数据类型，且源对象的属性值为null，则跳过此属性
                if (c.getType().isPrimitive() && Objects.isNull(value)) return;
                // 当value的类型与bean属性类型一致的时候进行copy
                if (isAssignable(c.getType(), value.getClass())) {
                    setValue2Bean(bean, c, value);
                }

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 对象映射为map
     *
     * @param obj 实体对象
     * @param <T>
     * @return 映射后的map集合
     */
    public static <T> Map<String, Object> obj2Map(T obj) {
        return obj2Map(obj, (String) null);
    }

    /**
     * 对象映射为map
     *
     * @param obj         实体对象
     * @param ignoreSplit 需要忽略的属性
     * @param <T>
     * @return 映射后的map集合
     */
    public static <T> Map<String, Object> obj2Map(T obj, String... ignoreSplit) {
        return obj2Map(ignoreSplit == null ? null : StreamUtils.joining(Arrays.stream(ignoreSplit), ","), obj, (String) null);
    }

    /**
     * 对象映射为map
     *
     * @param ignoreSplit 需要忽略的属性， 用符号[,]隔开
     * @param obj         实体对象
     * @param properties  可变参数，需要映射的属性
     * @param <T>
     * @return 映射后的map集合
     */
    public static <T> Map<String, Object> obj2Map(String ignoreSplit, T obj, String... properties) {
        // hashMap负载因子0.75
        Map<String, Object> retMap = new HashMap<>();
        if (obj == null) return retMap;

        Class<?> clazz = obj.getClass();
        Field[] allFields = getAllFields(clazz);
        // 获取需要忽略的属性
        Set<String> ignoreFields = new HashSet<>();
        if (StringUtils.isNotEmpty(ignoreSplit)) {
            ignoreFields = Arrays.stream(ignoreSplit.split(",")).filter(Objects::nonNull).map(String::trim).collect(Collectors.toSet());
        }

        // 复制所有属性
        if (CollectionUtils.isEmpty(properties)) {
            for (Field field : allFields) {
                // 对未忽略的属性进行拷贝操作
                if (!ignoreFields.contains(field.getName())) {
                    retMap.put(field.getName(), getValueFromBean(clazz, obj, field));
                }
            }
        } else {
            // 获取所有属性的名字
            Set<String> allFieldNames = Arrays.stream(allFields).map(Field::getName).collect(Collectors.toSet());
            // 获取需要拷贝的属性
            String[] copyFields = Arrays.stream(properties).filter(Objects::nonNull).map(String::trim).toArray(String[]::new);
            for (String copyField : copyFields) {
                if (ignoreFields.contains(copyField)) continue;// 忽略的字段
                // 如果对象中存在该属性，则执行复制的操作
                if (allFieldNames.contains(copyField)) {
                    retMap.put(copyField, getValueFromBean(obj, copyField));
                }
            }
        }

        return retMap;
    }
}
