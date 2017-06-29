package com.utils.redis.command;

import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * ZSet Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:03:10
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandZSetOperation extends DefaultCommandRedisOperation implements CommandZSetOperation {
	/**
	 * Interface ZSetOperations<K,V>. Redis ZSet/sorted set specific operations.
	 */
	protected ZSetOperations<String, String> zSetOps = super.opsForZSet();

	@Override
	public Boolean zAdd(String key, double score, String member) {
		return zSetOps.add(key, member, score);
	}

	@Override
	public Long zAdd(String key, Set<TypedTuple<String>> tuples) {
		return zSetOps.add(key, tuples);
	}
	
	@Override
	public Long zCard(String key) {
		return zSetOps.zCard(key);
	}
	
	@Override
	public Long zCount(String key, double min, double max) {
		return zSetOps.count(key, min, max);
	}
	
	@Override
	public Double zIncrBy(String key, double increment, String member) {
		return zSetOps.incrementScore(key, member, increment);
	}
	
	@Override
	public Set<String> zRange(String key, long start, long stop) {
		return zSetOps.rangeByScore(key, start, stop);
	}

	@Override
	public Set<TypedTuple<String>> zRangeWITHSCORES(String key, long start, long stop) {
		return zSetOps.rangeWithScores(key, start, stop);
	}

	@Override
	public Set<String> zRangeByScore(String key, double min, double max) {
		return zSetOps.rangeByScore(key, min, max);
	}

	@Override
	public Set<String> zRangeByScore(String key, double min, double max, long offset, long count) {
		return zSetOps.rangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<TypedTuple<String>> zRangeByScoreWITHSCORES(String key, double min, double max) {
		return zSetOps.rangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<TypedTuple<String>> zRangeByScoreWITHSCORES(String key, double min, double max, long offset, long count) {
		return zSetOps.rangeByScoreWithScores(key, min, max, offset, count);
	}
}
