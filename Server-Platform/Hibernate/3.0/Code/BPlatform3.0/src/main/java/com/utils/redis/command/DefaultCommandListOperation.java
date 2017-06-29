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
	public void bLPop(String key, long timeout) {
		listOps.leftPop(key, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void bRPop(String key, long timeout) {
		listOps.rightPop(key, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void bRPopLPush(String sourceKey, String destinationKey, long timeout) {
		listOps.rightPopAndLeftPush(sourceKey, destinationKey, timeout, TimeUnit.SECONDS);
	}

	@Override
	public String lIndex(String key, long index) {
		return listOps.index(key, index);
	}

	@Override
	public Long lInsert(String key, String position, String pivot, String value) {
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
	public Long lLen(String key) {
		return listOps.size(key);
	}
	
	@Override
	public String lPop(String key) {
		return listOps.leftPop(key);
	}

	@Override
	public Long lPush(String key, String value) {
		return listOps.leftPush(key, value);
	}
	
	@Override
	public Long lPush(String key, Collection<String> values) {
		return listOps.leftPushAll(key, values);
	}
	
	@Override
	public Long lPush(String key, String... values) {
		return listOps.leftPushAll(key, values);
	}
	
	@Override
	public Long lPushX(String key, String value) {
		return listOps.leftPushIfPresent(key, value);
	}

	@Override
	public List<String> lRange(String key, long start, long stop) {
		return listOps.range(key, start, stop);
	}
	
	@Override
	public Long lRem(String key, long count, Object value) {
		return listOps.remove(key, count, value);
	}
	
	@Override
	public void lSet(String key, long index, String value) {
		listOps.set(key, index, value);
	}
	
	@Override
	public void lTrim(String key, long start, long stop) {
		listOps.trim(key, start, stop);
	}
	
	@Override
	public String rPop(String key) {
		return listOps.rightPop(key);
	}

	@Override
	public String rPopLPush(String source, String destination) {
		return listOps.rightPopAndLeftPush(source, destination);
	}
	
	@Override
	public Long rPush(String key, String value) {
		return listOps.rightPush(key, value);
	}
	
	@Override
	public Long rPushX(String key, String value) {
		return listOps.rightPushIfPresent(key, value);
	}
}
