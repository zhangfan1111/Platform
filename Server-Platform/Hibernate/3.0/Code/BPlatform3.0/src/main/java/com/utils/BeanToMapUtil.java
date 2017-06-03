package com.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanToMapUtil {
	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param map
	 *            包含属性值的 map
	 * @param obj
	 *            bean对象
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static void convertMap(Map<Object, Object> map, Object obj) throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();

			if (map.containsKey(key)) {
				Object value = map.get(key);

				// 得到property对应的setter方法
				Method setter = property.getWriteMethod();
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				String _o = getter.getGenericReturnType().toString();
				if (_o.contains("Integer")) {
					Integer v = Integer.valueOf((String) value);
					setter.invoke(obj, v);
				} else if (_o.contains("String")) {
					String v = (String) value;
					setter.invoke(obj, v);
				} else if (_o.contains("Double")) {
					double v = Double.valueOf((String) value);
					setter.invoke(obj, v);
				} else if (_o.contains("Float")) {
					float v = Float.valueOf((String) value);
					setter.invoke(obj, v);
				} else if (_o.contains("Long")) {
					long v = Long.valueOf((String) value);
					setter.invoke(obj, v);
				} else if (_o.contains("Boolean")) {
					boolean v = Boolean.valueOf((boolean) value);
					setter.invoke(obj, v);
				} else if (_o.contains("Date")) {
					Date v = new Date(Long.valueOf((String) value));
					setter.invoke(obj, v);
				}
			}

		}
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map convertBean(Object bean) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Class type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					if (result instanceof Date) {
						returnMap.put(propertyName, String.valueOf(((Date) result).getTime()));
					} else {
						returnMap.put(propertyName, String.valueOf(result));
					}
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
}
