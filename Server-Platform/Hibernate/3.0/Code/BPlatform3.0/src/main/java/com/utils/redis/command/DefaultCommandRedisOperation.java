package com.utils.redis.command;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;

/**
 * Redis Command Operations.extends StringRedisTemplate.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午3:16:38
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandRedisOperation extends StringRedisTemplate implements CommandRedisOperation {

	@Override
	public void del(String key) {
		delete(key);
	}
	
	@Override
	public void del(Collection<String> keys) {
		delete(keys);
	}
	
	@Override
	public byte[] dump(String key) {
		return dump(key);
	}
	
	@Override
	public Boolean exists(String key) {
		return hasKey(key);
	}
	
	@Override
	public Boolean expire(String key, long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}
	
	@Override
	public Boolean expireAt(String key, long timestamp) {
		long unixTimestamp = timestamp * 1000;
		Date date = new Date(unixTimestamp);
		return expireAt(key, date);
	}
	
	@Override
	public Set<String> keys(String pattern) {
		return keys(pattern);
	}

	@Override
	public Boolean move(String key, int db) {
		return move(key, db);
	}

	@Override
	public Boolean persist(String key) {
		return persist(key);
	}

	@Override
	public Boolean pExpire(String key, long milliseconds) {
		return expire(key, milliseconds, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public Boolean pExpireAt(String key, long millisecondsTimestamp) {
		Date date = new Date(millisecondsTimestamp);
		return expireAt(key, date);
	}
	
	@Override
	public Long pTTL(String key) {
		return getExpire(key, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public String randomKey() {
		return randomKey();
	}
	
	@Override
	public void rename(String oldKey, String newKey) {
		rename(oldKey, newKey);
	}
	
	@Override
	public Boolean renameNX(String oldKey, String newKey) {
		return renameIfAbsent(oldKey, newKey);
	}
	
	@Override
	public void sort(String key, long timeToLive, byte[] serializedValue) {
		restore(key, serializedValue, timeToLive, TimeUnit.MILLISECONDS);
	}

	@Override
	public List<String> sort(SortQuery<String> sortQuery) {
		return sort(sortQuery);
	}

	@Override
	public Long ttl(String key) {
		return getExpire(key);
	}

	@Override
	public String redisType(String key) {
		DataType dataType = type(key);
		if(dataType != null) {
			return dataType.code();
		}
		return null;
	}
}
