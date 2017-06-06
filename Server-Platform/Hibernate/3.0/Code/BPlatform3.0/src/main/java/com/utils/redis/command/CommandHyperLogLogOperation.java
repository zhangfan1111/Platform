package com.utils.redis.command;

/**
 * Interface HyperLogLog Command Operations.
 * <ul>
 * <li>http://doc.redisfans.com/</li>
 * <li>http://www.runoob.com/redis</li>
 * </ul>
 * 
 * @author memory 2017年5月11日 下午9:04:47
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandHyperLogLogOperation {

	/**
	 * HyperLogLog 实现命令：PFADD key element [element ...]。将所有元素参数添加到 HyperLogLog 数据结构中。
	 * 
	 * <p>
	 * 可用版本：>= 2.8.9
	 * </p>
	 * 
	 * @author memory 2017年6月6日 上午10:18:29
	 * @param key
	 * @param values
	 * @return 如果至少有个元素被添加返回 1， 否则返回 0。
	 */
	Long PFADD(String key, String... values);

	/**
	 * HyperLogLog 实现命令：PFCOUNT key [key ...]。返回给定 HyperLogLog 的基数估算值。
	 * 
	 * <ul>
	 * <li>redis 127.0.0.1:6379> PFADD hll foo bar zap</li>
	 * <li>(integer) 1</li>
	 * <li>redis 127.0.0.1:6379> PFADD hll zap zap zap</li>
	 * <li>(integer) 0</li>
	 * <li>redis 127.0.0.1:6379> PFADD hll foo bar</li>
	 * <li>(integer) 0</li>
	 * <li>redis 127.0.0.1:6379> PFCOUNT hll</li>
	 * <li>(integer) 3</li>
	 * <li>redis 127.0.0.1:6379> PFADD some-other-hll 1 2 3</li>
	 * <li>(integer) 1</li>
	 * <li>redis 127.0.0.1:6379> PFCOUNT hll some-other-hll</li>
	 * <li>(integer) 6</li>
	 * </ul>
	 * 
	 * @author memory 2017年6月6日 上午10:27:25
	 * @param keys
	 * @return 返回给定 HyperLogLog 的基数值，如果多个 HyperLogLog 则返回基数估值之和。Gets the current number of elements within the key.
	 */
	Long PFCOUNT(String... keys);
	
	/**
	 * HyperLogLog 实现命令：PFDEL key。根据key删除相关值。
	 * 
	 * @author memory 2017年6月6日 上午10:32:27
	 * @param key
	 */
	void PFDEL(String key);
	
	/**
	 * HyperLogLog 实现命令：PFMERGE destkey sourcekey [sourcekey ...]。Merges all values of given sourceKeys into destination key.
	 * 
	 * <p>
	 * 将多个 HyperLogLog 合并为一个 HyperLogLog ，合并后的 HyperLogLog 的基数估算值是通过对所有 给定 HyperLogLog 进行并集计算得出的。
	 * </p>
	 * 
	 * @author memory 2017年6月6日 上午10:33:42
	 * @param destinationKey
	 * @param sourceKeys
	 * @return
	 */
	Long PFMERGE(String destinationKey, String... sourceKeys);
}
