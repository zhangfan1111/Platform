package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

/**
 * Interface Set Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:06:00
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandSetOperation {

	/**
	 * Set（集合） 实现命令：SADD key member，将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
	 * 
	 * <p>
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午12:03:20
	 * @param key
	 * @param member
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 */
	Long SADD(String key, String... members);

	/**
	 * Set（集合） 实现命令：SCARD key。返回集合 key 的基数(集合中元素的数量)。
	 * 
	 * @author memory 2017年6月1日 下午1:52:51
	 * @param key
	 * @return 集合的基数（长度）。当 key 不存在时，返回 0 。
	 */
	Long SCARD(String key);

	/**
	 * Set（集合） 实现命令：SDIFF key key。返回一个集合的全部成员，该集合是所有给定集合之间的差集。
	 * 
	 * @author memory 2017年6月1日 下午3:36:00
	 * @param key
	 * @param otherKey
	 * @return 交集成员的列表。
	 */
	Set<String> SDIFF(String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SDIFF key keys。返回一个集合的全部成员，该集合是所有给定集合之间的差集。
	 * 
	 * @author memory 2017年6月1日 下午3:36:00
	 * @param key
	 * @param otherKeys
	 * @return 交集成员的列表。
	 */
	Set<String> SDIFF(String key, Collection<String> otherKeys);

	/**
	 * Set（集合） 实现命令：SDIFFSTORE destination key key。这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
	 * 
	 * <p>
	 * 如果 destination 集合已经存在，则将其覆盖。estination 可以是 key 本身。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:52:30
	 * @param destinationKey
	 * @param key
	 * @param otherKey
	 * @return 结果集中的元素数量。
	 */
	Long SDIFFSTORE(String destinationKey, String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SDIFFSTORE destination key [key ...]。这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
	 * 
	 * <p>
	 * 如果 destination 集合已经存在，则将其覆盖。estination 可以是 key 本身。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:52:30
	 * @param destinationKey
	 * @param key
	 * @param otherKeys
	 * @return 结果集中的元素数量。
	 */
	Long SDIFFSTORE(String destinationKey, String key, Collection<String> otherKeys);

	/**
	 * Set（集合） 实现命令：SINTER key key。返回一个集合的全部成员，该集合是所有给定集合的交集。
	 * 
	 * <p>
	 * 不存在的 key 被视为空集。当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:56:12
	 * @param key
	 * @param otherKey
	 * @return 交集成员的列表。
	 */
	Set<String> SINTER(String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SINTER key [key ...]。返回一个集合的全部成员，该集合是所有给定集合的交集。
	 * 
	 * <p>
	 * 不存在的 key 被视为空集。当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:56:12
	 * @param key
	 * @param otherKeys
	 * @return 交集成员的列表。
	 */
	Set<String> SINTER(String key, Collection<String> otherKeys);

	/**
	 * Set（集合） 实现命令：SINTERSTORE destination key key。这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
	 * 
	 * <p>
	 * 如果 destination 集合已经存在，则将其覆盖。destination 可以是 key 本身。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:59:57
	 * @param destinationKey
	 * @param key
	 * @param otherKey
	 * @return 结果集中的成员数量。
	 */
	Long SINTERSTORE(String destinationKey, String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SINTERSTORE destination key [key...]。这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
	 * 
	 * <p>
	 * 如果 destination 集合已经存在，则将其覆盖。destination 可以是 key 本身。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午3:59:57
	 * @param destinationKey
	 * @param key
	 * @param otherKeys
	 * @return 结果集中的成员数量。
	 */
	Long SINTERSTORE(String destinationKey, String key, Collection<String> otherKeys);

	/**
	 * Set（集合） 实现命令：SISMEMBER key member。判断 member 元素是否集合 key 的成员。
	 * 
	 * @author memory 2017年6月1日 下午4:02:57
	 * @param key
	 * @param member
	 * @return 如果 member 元素不是集合的成员，或 key 不存在，返回false；否则返回true。
	 */
	Boolean SISMEMBER(String key, String member);

	/**
	 * Set（集合） 实现命令：SMEMBERS key，返回集合 key 中的所有成员
	 * 
	 * @author memory 2017年5月11日 下午12:03:55
	 * @param key
	 * @return 集合中的所有成员。
	 */
	Set<String> SMEMEBERS(String key);

	/**
	 * Set（集合） 实现命令：SMOVE source destination member。将 member 元素从 source 集合移动到 destination 集合。
	 * 
	 * <p>
	 * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 false。否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。
	 * </p>
	 * 
	 * <p>
	 * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午4:05:43
	 * @param source
	 * @param destination
	 * @param member
	 * @return 如果 member 元素被成功移除，返回 true 。如果 member 元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回 false 。
	 */
	Boolean SMOVE(String source, String destination, String member);

	/**
	 * Set（集合） 实现命令：SPOP key。移除并返回集合中的一个随机元素。
	 * 
	 * <p>
	 * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午4:10:38
	 * @param key
	 * @return 被移除的随机元素。
	 */
	String SPOP(String key);

	/**
	 * Set（集合） 实现命令：SRANDMEMBER key。如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
	 * 
	 * @author memory 2017年6月1日 下午4:12:46
	 * @param key
	 * @return 返回集合中的一个随机元素
	 */
	String SRANDMEMBER(String key);

	/**
	 * Set（集合） 实现命令：SRANDMEMBER key [count]。
	 * 
	 * <p>
	 * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
	 * </p>
	 * 
	 * <p>
	 * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
	 * </p>
	 * 
	 * <p>
	 * 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午4:13:52
	 * @param key
	 * @param count
	 * @return 返回一个集合；如果集合为空，返回空集合。
	 */
	List<String> SRANDMEMBER(String key, long count);

	/**
	 * Set（集合） 实现命令：SREM key member [member ...]。移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
	 * 
	 * @author memory 2017年6月1日 下午4:17:35
	 * @param key
	 * @param members
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 */
	Long SREM(String key, Object... members);

	/**
	 * Set（集合） 实现命令：SUNION key key。返回一个集合的全部成员，该集合是所有给定集合的并集。不存在的 key 被视为空集。
	 * 
	 * @author memory 2017年6月1日 下午4:20:49
	 * @param key
	 * @param otherKey
	 * @return 并集成员的列表。
	 */
	Set<String> SUNION(String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SUNION key [key...]。返回一个集合的全部成员，该集合是所有给定集合的并集。不存在的 key 被视为空集。
	 * 
	 * @author memory 2017年6月1日 下午4:20:53
	 * @param key
	 * @param otherKeys
	 * @return 并集成员的列表。
	 */
	Set<String> SUNION(String key, Collection<String> otherKeys);

	/**
	 * Set（集合） 实现命令：SUNIONSTORE destination key key。这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。如果 destination
	 * 已经存在，则将其覆盖。destination 可以是 key 本身。
	 * 
	 * @author memory 2017年6月1日 下午4:23:15
	 * @param destination
	 * @param key
	 * @param otherKey
	 * @return 结果集中的元素数量。
	 */
	Long SUNIONSTORE(String destination, String key, String otherKey);

	/**
	 * Set（集合） 实现命令：SUNIONSTORE destination key [key...]。这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。如果 destination
	 * 已经存在，则将其覆盖。destination 可以是 key 本身。
	 * @author memory 2017年6月1日 下午4:23:19
	 * @param destination
	 * @param key
	 * @param otherKeys
	 * @return 结果集中的元素数量
	 */
	Long SUNIONSTORE(String destination, String key, Collection<String> otherKeys);
	
	/**
	 * Set（集合） 实现命令：SSCAN key cursor [MATCH pattern] [COUNT count] 具体信息请参考 SCAN 命令。
	 * 
	 * @author memory 2017年5月11日 下午8:10:51
	 * @param key
	 * @param options
	 *            ScanOptions:Long count匹配长度；String pattern匹配值。
	 * @return 返回对象游标
	 */
	Cursor<String> SSCAN(String key, ScanOptions options);
}
