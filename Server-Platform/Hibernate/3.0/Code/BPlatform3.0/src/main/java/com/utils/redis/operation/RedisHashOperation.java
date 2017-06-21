package com.utils.redis.operation;

import java.util.List;
import java.util.Map;

public interface RedisHashOperation {
	/**
	 * 缓存实体对象
	 * @author memory 2017年5月10日 下午4:53:15
	 * @param o 需要缓存的对象
	 * @param key 键值
	 */
	public <T> void saveBeanObject(T o, Class<T> clazz, String key);
	
	/**
	 * 如果对象key以及hashkey不存在，缓存该对象
	 * Set the value of a hash hashKey only if hashKey does not exist.
	 * @author memory 2017年5月10日 下午5:11:12
	 * @param key 键值
	 * @param hashKey hash键值
	 * @param value 缓存的对象
	 */
	public boolean saveValueIfAbsent(String key, String hashKey, String value);
	
	/**
	 * 根据key和hashkey缓存对象
	 * @author memory 2017年5月10日 下午5:27:06
	 * @param key 键值
	 * @param hashKey hash键值
	 * @param value 缓存的对象
	 */
	public void saveValue(String key, String hashKey, String value);
	
	/**
	 * 获取相关键值对的size
	 * @author memory 2017年5月10日 下午5:29:55
	 * @param key 键值，非hash键值
	 * @return
	 */
	public Long size(String key);
	
	/**
	 * 根据键值对获取values集合
	 * @author memory 2017年5月10日 下午5:43:52
	 * @param key 键值
	 * @return
	 */
	public List<Object> valueList(String key);
	
	/**
	 * 根据键值获取map对象
	 * Get entire hash stored at key
	 * @author memory 2017年5月10日 下午6:03:12
	 * @param key 键值
	 * @return
	 */
	public Map<Object, Object> getMapEntries(String key);
	
	/**
	 * 根据键值获取bean对象
	 * @author memory 2017年5月10日 下午7:00:22
	 * @param key 键值
	 * @param o bean对象
	 */
	public <T> void getBeanEntry(T o, Class<T> clazz, String key);
	
	/**
	 * 根据键值key和hash键值判断是否已经包含相关缓存值
	 * @author memory 2017年5月10日 下午7:08:09
	 * @param key 键值
	 * @param hashKey hash键值
	 * @return
	 */
	public boolean hasKey(String key, Object hashKey);
	
	/**
	 * 给对应键值key和hash键值增加delta值
	 * @author memory 2017年5月10日 下午7:14:46
	 * @param key 键值
	 * @param hashKey hash键值
	 * @param delta 需要增加的值，类型：long、double
	 * @return
	 */
	public Object increment(String key, Object hashKey, Object delta);
}
