package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;

/**
 * List Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:02:09
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandListOperation extends DefaultCommandRedisOperation implements CommandListOperation {
	/**
	 * Interface ListOperations<K,V>. Redis list specific operations.
	 */
	protected ListOperations<String, String> listOps = super.opsForList();

	@Override
	public void BLPOP(String key, long timeout) {
		listOps.leftPop(key, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void BRPOP(String key, long timeout) {
		listOps.rightPop(key, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void BRPOPLPUSH(String sourceKey, String destinationKey, long timeout) {
		listOps.rightPopAndLeftPush(sourceKey, destinationKey, timeout, TimeUnit.SECONDS);
	}

	@Override
	public String LINDEX(String key, long index) {
		return listOps.index(key, index);
	}

	@Override
	public Long LINSERT(String key, String position, String pivot, String value) {
		Long size = -1l;
		switch (position) {
		case "BEFORE":
			size = listOps.leftPush(key, pivot, value);
			break;
		case "AFTER":
			size = listOps.rightPush(key, pivot, value);
			break;
		}
		return size;
	}

	@Override
	public Long LLEN(String key) {
		return listOps.size(key);
	}
	
	@Override
	public String LPOP(String key) {
		return listOps.leftPop(key);
	}

	@Override
	public Long LPUSH(String key, String value) {
		return listOps.leftPush(key, value);
	}
	
	@Override
	public Long LPUSH(String key, Collection<String> values) {
		return listOps.leftPushAll(key, values);
	}
	
	@Override
	public Long LPUSH(String key, String... values) {
		return listOps.leftPushAll(key, values);
	}
	
	@Override
	public Long LPUSHX(String key, String value) {
		return listOps.leftPushIfPresent(key, value);
	}

	@Override
	public List<String> LRANGE(String key, long start, long stop) {
		return listOps.range(key, start, stop);
	}
	
	@Override
	public Long LREM(String key, long count, Object value) {
		return listOps.remove(key, count, value);
	}
	
	@Override
	public void LSET(String key, long index, String value) {
		listOps.set(key, index, value);
	}
	
	@Override
	public void LTRIM(String key, long start, long stop) {
		listOps.trim(key, start, stop);
	}
	
	@Override
	public String RPOP(String key) {
		return listOps.rightPop(key);
	}

	@Override
	public String RPOPLPUSH(String source, String destination) {
		return listOps.rightPopAndLeftPush(source, destination);
	}
	
	@Override
	public Long RPUSH(String key, String value) {
		return listOps.rightPush(key, value);
	}
	
	@Override
	public Long RPUSHX(String key, String value) {
		return listOps.rightPushIfPresent(key, value);
	}
}
