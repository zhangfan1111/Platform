package com.utils.redis.operation;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;

import com.memory.platform.common.util.StringUtil;
import com.utils.BeanToMapUtil;
import com.utils.redis.command.DefaultCommandHashOperation;
import com.utils.redis.hash.BeanUtilsHashMapper2;

public class DefaultRedisHashOperation extends DefaultCommandHashOperation implements RedisHashOperation{
	private static final Logger logger = LoggerFactory.getLogger(DefaultRedisHashOperation.class);
	
//	@Autowired
//	private RedisTemplate redisTemplate;
	
	@Override
	public <T> void saveBeanObject(T o, Class<T> clazz, String key) {
		//自定义	BeanToMapUtil 类，实现bean转map	
//		Map<Object, Object> map;
//		try {
//			map = BeanToMapUtil.convertBean(o);
//			HMSET(key, map);
//		} catch (IllegalAccessException e) {
//			logger.error("Methed : saveBeanObject: " + o.getClass().getName() + " : IllegalAccessException : " + e.getMessage());
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			logger.error("Methed : saveBeanObject: " + o.getClass().getName() + " : InvocationTargetException : " + e.getMessage());
//			e.printStackTrace();
//		} catch (IntrospectionException e) {
//			logger.error("Methed : saveBeanObject: " + o.getClass().getName() + " : IntrospectionException : " + e.getMessage());
//			e.printStackTrace();
//		}
		
		//重写BeanUtilsHashMapper（BeanUtilsHashMapper2），通过HashMapper实现同上功能
		HashMapper<T, String, String> mapper = new DecoratingStringHashMapper<T>(new BeanUtilsHashMapper2<T>(clazz));
		Map map = mapper.toHash(o);
		HMSET(key, map);
	}

	@Override
	public boolean saveValueIfAbsent(String key, String hashKey, String value) {
		boolean flag = false;
		if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(hashKey) && StringUtil.isNotEmpty(value)) {
			flag = HSETNX(key, hashKey, value);
		}
		return flag;
	}

	@Override
	public void saveValue(String key, String hashKey, String value) {
		HSET(key, hashKey, value);
	}

	@Override
	public Long size(String key) {
		Long len = 0l;
		if(StringUtil.isNotEmpty(key)) {
			len = HLEN(key);
		}
		return len;
	}

	@Override
	public List<Object> valueList(String key) {
		if(StringUtil.isNotEmpty(key)) {
			List<Object> list = HVALS(key);
			return list;
		}
		return null;
	}

	@Override
	public Map<Object, Object> getMapEntries(String key) {
		Map<Object, Object> map = null;
		if(StringUtil.isNotEmpty(key)) {
			map = HGETALL(key);
		}
		return map;
	}

	@Override
	public <T> void getBeanEntry(T o, Class<T> clazz, String key) {
		if(StringUtil.isNotEmpty(key) && o != null) {
			Map map = getMapEntries(key);
			
			//自定义	BeanToMapUtil 类，实现bean转map	
//			try {
//				BeanToMapUtil.convertMap(map, o);
//			} catch (IllegalAccessException e) {
//				logger.error("Methed : getBeanEntry: " + o.getClass().getName() + " : IllegalAccessException : " + e.getMessage());
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				logger.error("Methed : getBeanEntry: " + o.getClass().getName() + " : IllegalArgumentException : " + e.getMessage());
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				logger.error("Methed : getBeanEntry: " + o.getClass().getName() + " : InvocationTargetException : " + e.getMessage());
//				e.printStackTrace();
//			} catch (IntrospectionException e) {
//				logger.error("Methed : getBeanEntry: " + o.getClass().getName() + " : IntrospectionException : " + e.getMessage());
//				e.printStackTrace();
//			}
			
			//重写BeanUtilsHashMapper（BeanUtilsHashMapper2），通过HashMapper实现同上功能
			HashMapper<T, String, String> mapper = new DecoratingStringHashMapper<T>(new BeanUtilsHashMapper2<T>(clazz));
			o = mapper.fromHash(map);
		}
	}

	@Override
	public boolean hasKey(String key, Object hashKey) {
		return HEXISTS(key, hashKey);
	}

	@Override
	public Object increment(String key, Object hashKey, Object delta) {
		Object result = 0;
		if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(hashKey)) {
			if(delta instanceof Long) {
				result = HINCRBY(key, hashKey, (long) delta);
			} else if(delta instanceof Double) {
				result = HINCRBYFLOAT(key, hashKey, (double) delta);
			}
		}
		return result;
	}
}
