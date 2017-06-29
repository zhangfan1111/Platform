package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ValueOperations;

/**
 * Value Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:02:39
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandValueOperation extends DefaultCommandRedisOperation implements CommandValueOperation {
	/**
	 * Interface ValueOperations<K,V>. Redis operations for simple (or in Redis terminology 'string') values.
	 */
	protected ValueOperations<String, String> valOps = super.opsForValue();

	@Override
	public Integer append(String key, String value) {
		return valOps.append(key, value);
	}

	@Override
	public Long decr(String key) {
		return valOps.increment(key, -1l);
	}

	@Override
	public Long decrBy(String key, long decrement) {
		if(decrement > 0l) {
			return 0l;
		}
		return valOps.increment(key, decrement);
	}

	@Override
	public Double decrByFloat(String key, double decrement) {
		if(decrement > 0d) {
			return 0d;
		}
		return valOps.increment(key, decrement);
	}

	@Override
	public String redisGet(Object key) {
		return valOps.get(key);
	}

	@Override
	public Boolean getBit(String key, long offset) {
		return valOps.getBit(key, offset);
	}

	@Override
	public String getRange(String key, long start, long end) {
		return valOps.get(key, start, end);
	}

	@Override
	public String getSet(String key, String value) {
		String oldValue = redisGet(key);
		redisSet(key, value);
		return oldValue;
	}

	@Override
	public Long incr(String key) {
		return valOps.increment(key, 1l);
	}

	@Override
	public Long incrBy(String key, long increment) {
		return valOps.increment(key, increment);
	}

	@Override
	public Double incrByFloat(String key, double increment) {
		return valOps.increment(key, increment);
	}

	@Override
	public List<String> mGet(Collection<String> keys) {
		return valOps.multiGet(keys);
	}

	@Override
	public void mSet(Map<String, String> map) {
		valOps.multiSet(map);
	}
	
	@Override
	public Boolean mSetNX(Map<String, String> map) {
		return valOps.multiSetIfAbsent(map);
	}
	
	@Override
	public void redisSet(String key, String value) {
		valOps.set(key, value);
	}

	@Override
	public Boolean setBit(String key, long offset, boolean value) {
		return valOps.setBit(key, offset, value);
	}

	@Override
	public void setEx(String key, long timeout, String value) {
		valOps.set(key, value, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void setNx(String key, String value) {
		valOps.setIfAbsent(key, value);
	}

	@Override
	public void setRange(String key, long offset, String value) {
		valOps.set(key, value, offset);
	}

	@Override
	public Long strLen(String key) {
		return valOps.size(key);
	}

}
