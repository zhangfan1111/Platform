package com.utils.redis.command;

import org.springframework.data.redis.core.HyperLogLogOperations;

/**
 * HyperLogLog Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:01:49
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandHyperLogLogOperation extends DefaultCommandRedisOperation implements CommandHyperLogLogOperation {
	/**
	 * Interface HyperLogLogOperations<K,V>.
	 */
	protected HyperLogLogOperations<String, String> hllOps = super.opsForHyperLogLog();
}
