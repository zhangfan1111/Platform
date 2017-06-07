package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

/**
 * Set Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:02:26
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandSetOperation extends DefaultCommandRedisOperation implements CommandSetOperation {
	/**
	 * Interface SetOperations<K,V>. Redis set specific operations.
	 */
	protected SetOperations<String, String> setOps = super.opsForSet();

	@Override
	public Long SADD(String key, String... members) {
		return setOps.add(key, members);
	}

	@Override
	public Long SCARD(String key) {
		return setOps.size(key);
	}
	
	@Override
	public Set<String> SDIFF(String key, String otherKey) {
		return setOps.difference(key, otherKey);
	}

	@Override
	public Set<String> SDIFF(String key, Collection<String> otherKeys) {
		return setOps.difference(key, otherKeys);
	}

	@Override
	public Long SDIFFSTORE(String destinationKey, String key, String otherKey) {
		return setOps.differenceAndStore(key, otherKey, destinationKey);
	}

	@Override
	public Long SDIFFSTORE(String destinationKey, String key, Collection<String> otherKeys) {
		return setOps.differenceAndStore(key, otherKeys, destinationKey);
	}
	
	@Override
	public Set<String> SINTER(String key, String otherKey) {
		return setOps.intersect(key, otherKey);
	}

	@Override
	public Set<String> SINTER(String key, Collection<String> otherKeys) {
		return setOps.intersect(key, otherKeys);
	}
	
	@Override
	public Long SINTERSTORE(String destinationKey, String key, String otherKey) {
		return setOps.intersectAndStore(key, otherKey, destinationKey);
	}

	@Override
	public Long SINTERSTORE(String destinationKey, String key, Collection<String> otherKeys) {
		return setOps.intersectAndStore(key, otherKeys, destinationKey);
	}
	
	@Override
	public Boolean SISMEMBER(String key, String member) {
		return setOps.isMember(key, member);
	}
	
	@Override
	public Set<String> SMEMEBERS(String key) {
		return setOps.members(key);
	}

	@Override
	public Boolean SMOVE(String source, String destination, String member) {
		return setOps.move(source, member, destination);
	}

	@Override
	public String SPOP(String key) {
		return setOps.pop(key);
	}

	@Override
	public String SRANDMEMBER(String key) {
		return setOps.randomMember(key);
	}

	@Override
	public List<String> SRANDMEMBER(String key, long count) {
		return setOps.randomMembers(key, count);
	}

	@Override
	public Long SREM(String key, Object... members) {
		return setOps.remove(key, members);
	}

	@Override
	public Set<String> SUNION(String key, String otherKey) {
		return setOps.union(key, otherKey);
	}

	@Override
	public Set<String> SUNION(String key, Collection<String> otherKeys) {
		return setOps.union(key, otherKeys);
	}

	@Override
	public Long SUNIONSTORE(String destination, String key, String otherKey) {
		return setOps.unionAndStore(key, otherKey, destination);
	}

	@Override
	public Long SUNIONSTORE(String destination, String key, Collection<String> otherKeys) {
		return setOps.unionAndStore(key, otherKeys, destination);
	}

	@Override
	public Cursor<String> SSCAN(String key, ScanOptions options) {
		return setOps.scan(key, options);
	}

}
