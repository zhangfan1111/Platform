package com.k2data.k2app.utils;

import com.k2data.k2app.exception.K2Exception;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author lidong9144@163.com 17-3-10.
 */
public class ObjectUtils {

    /**
     * 转换目标{@code T}实例为字符串，{@code null}转为{@code ""}<b>
     * 该方法主要用于在HTML(jsp)界面上显示
     *
     * @param target 要转换的{@code T}实例
     * @return 已转换的字符串
     */
    public static <T> String toString(final T target) {
        if (target == null) {
            return "";
        }

        return ConvertUtils.convert(target, String.class, "");
    }

    /**
     * 判断是否为空,只判断如下几种情况
     * 1. Null
     * 2. CharSequence or StringBuilder or StringBuffer = ""
     * 3. Map.size = 0
     * 4. Array.size = 0
     * 5. Collection.size = 0
     *
     * @param target 被判断的Object
     * @return true 如果空
     */
    public static boolean isEmpty(final Object target) {
        if (target == null)
            return true;

        final Class<?> clazz = target.getClass();

        // 判断是不是CharSequence
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return ((CharSequence) target).length() == 0;
        }

        // 判断是不是StringBuilder
        if (target instanceof StringBuilder)
            return ((StringBuilder) target).length() == 0;

        // 判断是不是StringBuffer
        if (target instanceof StringBuffer)
            return ((StringBuffer) target).length() == 0;

        // 判断是不是array
        if (clazz.isArray()) {
            return Array.getLength(target) == 0;
        }

        // 判断是不是Collection
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) target).size() == 0;
        }

        // 判断是不是Map
        return Map.class.isAssignableFrom(clazz) && ((Map<?, ?>) target).size() == 0;

    }

    /**
     * 深度克隆方法，对象必须是可序列化的
     *
     * @param <T> 要克隆的类
     * @param target 要被克隆的实例
     * @return 克隆的出来的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(final T target) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(target);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

            ObjectInputStream ois = new ObjectInputStream(bis);

            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            throw new K2Exception(ioe);
        }
    }

    /**
     * 创建指定类型对应的"空"值，如<br>
     * <ul>
     * 基本类型={@code 0}<br>
     * String={@code ""}<br>
     * Number={@code 0}<br>
     * 其他={@code null}<br>
     * </ul>
     *
     * @param type 指定的类型
     * @return 对应指定类型的"空"值
     */
    @SuppressWarnings("unchecked")
    public static <T> T emptyObject(final Class<T> type) {
        if (type == null)
            return null;

        if (ClassUtils.isPrimitiveOrWrapper(type)) {
            return ConvertUtils.convert(0, type);
        }

        if (Number.class.isAssignableFrom(type)) {
            return ConvertUtils.convert(0, type);
        } else if (String.class.equals(type)) {
            return (T) "";
        }

        return null;
    }

}
