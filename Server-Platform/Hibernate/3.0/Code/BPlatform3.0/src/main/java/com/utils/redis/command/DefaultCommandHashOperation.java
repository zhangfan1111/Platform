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
	public void hDel(String key, Object... fields) {
		hashOps.delete(key, fields);
	}

	@Override
	public boolean hExists(String key, Object field) {
		return hashOps.hasKey(key, field);
	}

	@Override
	public Long hIncrBy(String key, Object hashKey, long increment) {
		return hashOps.increment(key, hashKey, increment);
	}

	@Override
	public Double hIncrByFloat(String key, Object hashKey, double increment) {
		return hashOps.increment(key, hashKey, increment);
	}

	@Override
	public Set<Object> hKeys(String key) {
		return hashOps.keys(key);
	}

	@Override
	public Long hLen(String key) {
		return hashOps.size(key);
	}

	@Override
	public List<Object> hmGet(String key, Collection<Object> hashKeys) {
		return hashOps.multiGet(key, hashKeys);
	}

	@Override
	public void hmSet(String key, Map<Object, Object> map) {
		hashOps.putAll(key, map);
	}

	@Override
	public List<Object> hVals(String key) {
		return hashOps.values(key);
	}

	@Override
	public void hSet(String key, Object field, Object value) {
		hashOps.put(key, field, value);
	}

	@Override
	public boolean hSetNX(String key, Object field, Object value) {
		return hashOps.putIfAbsent(key, field, value);
	}

	@Override
	public String hGet(String key, Object field) {
		return (String) hashOps.get(key, field);
	}

	@Override
	public Map<Object, Object> hGetAll(String key) {
		return hashOps.entries(key);
	}

	@Override
	public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
		return hashOps.scan(key, options);
	}
}
