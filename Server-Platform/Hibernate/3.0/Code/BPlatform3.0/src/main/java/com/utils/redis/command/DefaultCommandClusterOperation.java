package com.utils.redis.command;

import org.springframework.data.redis.core.ClusterOperations;

/**
 * Cluster Command Operations.extends RedisCommandOperation.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:01:07
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public class DefaultCommandClusterOperation extends DefaultCommandRedisOperation implements CommandClusterOperation {
	/**
	 * Interface ClusterOperations<K,V>. Redis operations for cluster specific operations. A RedisClusterNode can be
	 * obtained from a connection or it can be constructed using either host and RedisNode.getPort() or the node Id.
	 */
	protected ClusterOperations<String, String> clusterOps = super.opsForCluster();
}
