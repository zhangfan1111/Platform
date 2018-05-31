package com.k2data.k2app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 用 orika 代替
 *
 * @author lidong9144@163.com 17-3-24.
 */
@Deprecated
public class PojoUtils {

    /**
     * 复制{@link Object}的成员变量的值到目标{@link Object}的相同名称的的成员变量上，不区分大小写。
     *
     * @param provider 提供值的{@link Object}实例
     * @param accepter 接受数据的{@link Object}实例
     */
    public static void copyProperties(Object provider, Object accepter) {
        final Class<?> providerCls = provider.getClass();
        final Class<?> accepterCls = accepter.getClass();

        for (Class<?> cls = providerCls; cls != null; cls = cls.getSuperclass()) {
            for (Field providerField : cls.getDeclaredFields()) {
                final String propertyName = providerField.getName();

                final Method providerReader = ReflectUtils.getReadMethod(cls, propertyName);

                if (providerReader == null) {
                    continue;
                }

                final Method accepterWriter = ReflectUtils.getWriteMethod(accepterCls, propertyName);

                if (accepterWriter == null) {
                    continue;
                }


                final Class<?> parameterType = accepterWriter.getParameterTypes()[0];

                Object paramValue = ConvertUtils.convert(ReflectUtils.invokeMethod(provider, providerReader), parameterType);

                if (paramValue != null) {
                    ReflectUtils.invokeMethod(accepter, accepterWriter, paramValue);
                }
            }
        }
    }

    /**
     * 复制{@link Map}的值，用{@code Setter}方法赋值到{@link Map}的{@code KEY}对应的目标{@link Object}的成员变量上
     * 其中如果{@code prefix}和{@code suffix}非空的话，就要从{@link Map}的{@code KEY}中去掉前后缀后比较
     *
     * @param provider 提供值的{@link Map}实例
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public static <T> void copyProperties(final Map<String, T> provider, final Object accepter, final String prefix, final String suffix) {
        final String vPrefix = ((StringUtils.isBlank(prefix))? "" : prefix);
        final String vSuffix = ((StringUtils.isBlank(suffix))? "" : suffix);

        final Class<?> cls = accepter.getClass();

        for (Map.Entry<String, T> entry : provider.entrySet()) {
            final String key = entry.getKey();

            if (!StringUtils.startsWithIgnoreCase(key, vPrefix) || !StringUtils.endsWithIgnoreCase(key, vSuffix)) {
                continue;
            }

            final String name = StringUtils.removeEnd(StringUtils.removeStart(key.toLowerCase(), vPrefix.toLowerCase()), vSuffix.toLowerCase());

            if (StringUtils.isBlank(name))
                continue;

            final T value = provider.get(key);

            final Method writer = ReflectUtils.getWriteMethod(cls, name);

            if (writer != null) {
                final Class<?> parameterType = writer.getParameterTypes()[0];

                Object paramValue = ConvertUtils.convert(value, parameterType);

                if (paramValue != null) {
                    ReflectUtils.invokeMethod(accepter, writer, paramValue);
                }
            }
        }
    }

}
