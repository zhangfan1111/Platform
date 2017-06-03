package com.utils.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.SerializationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json序列化工具
 * <p>
 * 不使用sdr自带的json序列化工具，一切操作基于string
 * </p>
 * 
 * @author memory 2017年5月15日 下午5:12:50
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月15日
 * @modify by reason:{方法名}:{原因}
 */
public class JsonRedisSerializer {

	public static final String EMPTY_JSON = "{}";

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	protected ObjectMapper objectMapper = new ObjectMapper();

	public JsonRedisSerializer() {
	}

	/**
	 * java-object as json-string
	 * 
	 * @author memory 2017年5月15日 下午5:19:20
	 * @param object
	 * @return
	 */
	public String seriazileAsString(Object object) {
		if (object == null) {
			return EMPTY_JSON;
		}
		try {
			return this.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
		}
	}

	/**
	 * json-string to java-object
	 * 
	 * @author memory 2017年5月15日 下午5:22:47
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public <T> T deserializeAsObject(String jsonStr, Class<T> clazz) {
		if (jsonStr == null || clazz == null) {
			return null;
		}

		try {
			return this.objectMapper.readValue(jsonStr, clazz);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}
}
