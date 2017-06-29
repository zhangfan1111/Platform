package com.utils.redis.command;

import org.springframework.data.redis.core.HyperLogLogOperations;

/**
 * HyperLogLog Command Operations.extends RedisCommandOperation.
 * <ul>
 * <li>http://doc.redisfans.com/</li>
 * <li>http://www.runoob.com/redis</li>
 * </ul>
 * 
 * <p>
 * Redis 在 2.8.9 版本添加了 HyperLogLog 结构。
 * </p>
 * <p>
 * Redis HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定 的、并且是很小的。
 * </p>
 * <p>
 * 在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基 数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。
 * </p>
 * <p>
 * 但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
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

	@Override
	public Long pfAdd(String key, String... values) {
		return hllOps.add(key, values);
	}

	@Override
	public Long pfCount(String... keys) {
		return hllOps.size(keys);
	}

	@Override
	public void pfDel(String key) {
		hllOps.delete(key);
	}

	@Override
	public Long pfMerge(String destinationKey, String... sourceKeys) {
		return hllOps.union(destinationKey, sourceKeys);
	}
}
