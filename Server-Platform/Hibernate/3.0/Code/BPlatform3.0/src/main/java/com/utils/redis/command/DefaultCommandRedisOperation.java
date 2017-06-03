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
	public void DEL(String key) {
		delete(key);
	}
	
	@Override
	public void DEL(Collection<String> keys) {
		delete(keys);
	}
	
	@Override
	public byte[] DUMP(String key) {
		return dump(key);
	}
	
	@Override
	public Boolean EXISTS(String key) {
		return hasKey(key);
	}
	
	@Override
	public Boolean EXPIRE(String key, long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}
	
	@Override
	public Boolean EXPIREAT(String key, long timestamp) {
		long unixTimestamp = timestamp * 1000;
		Date date = new Date(unixTimestamp);
		return expireAt(key, date);
	}
	
	@Override
	public Set<String> KEYS(String pattern) {
		return keys(pattern);
	}

	@Override
	public Boolean MOVE(String key, int db) {
		return move(key, db);
	}

	@Override
	public Boolean PERSIST(String key) {
		return persist(key);
	}

	@Override
	public Boolean PEXPIRE(String key, long milliseconds) {
		return expire(key, milliseconds, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public Boolean PEXPIREAT(String key, long millisecondsTimestamp) {
		Date date = new Date(millisecondsTimestamp);
		return expireAt(key, date);
	}
	
	@Override
	public Long PTTL(String key) {
		return getExpire(key, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public String RANDOMKEY() {
		return randomKey();
	}
	
	@Override
	public void RENAME(String oldKey, String newKey) {
		rename(oldKey, newKey);
	}
	
	@Override
	public Boolean RENAMENX(String oldKey, String newKey) {
		return renameIfAbsent(oldKey, newKey);
	}
	
	@Override
	public void RESTORE(String key, long timeToLive, byte[] serializedValue) {
		restore(key, serializedValue, timeToLive, TimeUnit.MILLISECONDS);
	}

	@Override
	public List<String> SORT(SortQuery<String> sortQuery) {
		return sort(sortQuery);
	}

	@Override
	public Long TTL(String key) {
		return getExpire(key);
	}

	@Override
	public String TYPE(String key) {
		DataType dataType = type(key);
		if(dataType != null) {
			return dataType.code();
		}
		return null;
	}
}
