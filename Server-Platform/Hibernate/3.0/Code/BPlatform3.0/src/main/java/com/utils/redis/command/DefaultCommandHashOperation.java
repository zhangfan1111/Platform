package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;

/**
 * Hash Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:01:33
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandHashOperation extends DefaultCommandRedisOperation implements CommandHashOperation {
	/**
	 * Interface HashOperations<H,HK,HV>. Redis map specific operations working on a hash.
	 */
	protected HashOperations<String, Object, Object> hashOps = super.opsForHash();

	@Override
	public void HDEL(String key, Object... fields) {
		hashOps.delete(key, fields);
	}

	@Override
	public boolean HEXISTS(String key, Object field) {
		return hashOps.hasKey(key, field);
	}

	@Override
	public Long HINCRBY(String key, Object hashKey, long increment) {
		return hashOps.increment(key, hashKey, increment);
	}

	@Override
	public Double HINCRBYFLOAT(String key, Object hashKey, double increment) {
		return hashOps.increment(key, hashKey, increment);
	}

	@Override
	public Set<Object> HKEYS(String key) {
		return hashOps.keys(key);
	}

	@Override
	public Long HLEN(String key) {
		return hashOps.size(key);
	}

	@Override
	public List<Object> HMGET(String key, Collection<Object> hashKeys) {
		return hashOps.multiGet(key, hashKeys);
	}

	@Override
	public void HMSET(String key, Map<Object, Object> map) {
		hashOps.putAll(key, map);
	}

	@Override
	public List<Object> HVALS(String key) {
		return hashOps.values(key);
	}

	@Override
	public void HSET(String key, Object field, Object value) {
		hashOps.put(key, field, value);
	}

	@Override
	public boolean HSETNX(String key, Object field, Object value) {
		return hashOps.putIfAbsent(key, field, value);
	}

	@Override
	public String HGET(String key, Object field) {
		return (String) hashOps.get(key, field);
	}

	@Override
	public Map<Object, Object> HGETALL(String key) {
		return hashOps.entries(key);
	}

	@Override
	public Cursor<Map.Entry<Object, Object>> HSCAN(String key, ScanOptions options) {
		return hashOps.scan(key, options);
	}
}
