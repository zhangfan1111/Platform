package com.utils.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * JedisUtil
 * </p>
 * 
 * @author memory 2017年5月5日 下午3:41:11
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月5日
 * @modify by reason:{方法名}:{原因}
 */
public class JedisUtil {
	/**
	 * serialize Object
	 * 
	 * @author memory 2017年5月5日 下午3:41:24
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * unserialize Object
	 * 
	 * @author memory 2017年5月5日 下午3:41:31
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @author memory 2017年5月5日 下午4:20:06
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}

		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}

		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			long length = object.length;
			if (length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
}
