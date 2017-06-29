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
	public Long sAdd(String key, String... members) {
		return setOps.add(key, members);
	}

	@Override
	public Long sCard(String key) {
		return setOps.size(key);
	}
	
	@Override
	public Set<String> sDiff(String key, String otherKey) {
		return setOps.difference(key, otherKey);
	}

	@Override
	public Set<String> sDiff(String key, Collection<String> otherKeys) {
		return setOps.difference(key, otherKeys);
	}

	@Override
	public Long sDiffStore(String destinationKey, String key, String otherKey) {
		return setOps.differenceAndStore(key, otherKey, destinationKey);
	}

	@Override
	public Long sDiffStore(String destinationKey, String key, Collection<String> otherKeys) {
		return setOps.differenceAndStore(key, otherKeys, destinationKey);
	}
	
	@Override
	public Set<String> sInter(String key, String otherKey) {
		return setOps.intersect(key, otherKey);
	}

	@Override
	public Set<String> sInter(String key, Collection<String> otherKeys) {
		return setOps.intersect(key, otherKeys);
	}
	
	@Override
	public Long sInterStore(String destinationKey, String key, String otherKey) {
		return setOps.intersectAndStore(key, otherKey, destinationKey);
	}

	@Override
	public Long sInterStore(String destinationKey, String key, Collection<String> otherKeys) {
		return setOps.intersectAndStore(key, otherKeys, destinationKey);
	}
	
	@Override
	public Boolean sisMember(String key, String member) {
		return setOps.isMember(key, member);
	}
	
	@Override
	public Set<String> sMembers(String key) {
		return setOps.members(key);
	}

	@Override
	public Boolean sMove(String source, String destination, String member) {
		return setOps.move(source, member, destination);
	}

	@Override
	public String sPop(String key) {
		return setOps.pop(key);
	}

	@Override
	public String sRandMember(String key) {
		return setOps.randomMember(key);
	}

	@Override
	public List<String> sRandMember(String key, long count) {
		return setOps.randomMembers(key, count);
	}

	@Override
	public Long sRem(String key, Object... members) {
		return setOps.remove(key, members);
	}

	@Override
	public Set<String> sUnion(String key, String otherKey) {
		return setOps.union(key, otherKey);
	}

	@Override
	public Set<String> sUnion(String key, Collection<String> otherKeys) {
		return setOps.union(key, otherKeys);
	}

	@Override
	public Long sUnionStore(String destination, String key, String otherKey) {
		return setOps.unionAndStore(key, otherKey, destination);
	}

	@Override
	public Long sUnionStore(String destination, String key, Collection<String> otherKeys) {
		return setOps.unionAndStore(key, otherKeys, destination);
	}

	@Override
	public Cursor<String> sScan(String key, ScanOptions options) {
		return setOps.scan(key, options);
	}

}
