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
	public Integer APPEND(String key, String value) {
		return valOps.append(key, value);
	}

	@Override
	public Long DECR(String key) {
		return valOps.increment(key, -1l);
	}

	@Override
	public Long DECRBY(String key, long decrement) {
		if(decrement > 0l) {
			return 0l;
		}
		return valOps.increment(key, decrement);
	}

	@Override
	public Double DECRBYFloat(String key, double decrement) {
		if(decrement > 0d) {
			return 0d;
		}
		return valOps.increment(key, decrement);
	}

	@Override
	public String GET(Object key) {
		return valOps.get(key);
	}

	@Override
	public Boolean GETBIT(String key, long offset) {
		return valOps.getBit(key, offset);
	}

	@Override
	public String GETRANGE(String key, long start, long end) {
		return valOps.get(key, start, end);
	}

	@Override
	public String GETSET(String key, String value) {
		String oldValue = GET(key);
		SET(key, value);
		return oldValue;
	}

	@Override
	public Long INCR(String key) {
		return valOps.increment(key, 1l);
	}

	@Override
	public Long INCRBY(String key, long increment) {
		return valOps.increment(key, increment);
	}

	@Override
	public Double INCRBYFLOAT(String key, double increment) {
		return valOps.increment(key, increment);
	}

	@Override
	public List<String> MGET(Collection<String> keys) {
		return valOps.multiGet(keys);
	}

	@Override
	public void MSET(Map<String, String> map) {
		valOps.multiSet(map);
	}
	
	@Override
	public Boolean MSETNX(Map<String, String> map) {
		return valOps.multiSetIfAbsent(map);
	}
	
	@Override
	public void SET(String key, String value) {
		valOps.set(key, value);
	}

	@Override
	public Boolean SETBIT(String key, long offset, boolean value) {
		return valOps.setBit(key, offset, value);
	}

	@Override
	public void SETEX(String key, long timeout, String value) {
		valOps.set(key, value, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void SETNX(String key, String value) {
		valOps.setIfAbsent(key, value);
	}

	@Override
	public void SETRANGE(String key, long offset, String value) {
		valOps.set(key, value, offset);
	}

	@Override
	public Long STRLEN(String key) {
		return valOps.size(key);
	}

}
