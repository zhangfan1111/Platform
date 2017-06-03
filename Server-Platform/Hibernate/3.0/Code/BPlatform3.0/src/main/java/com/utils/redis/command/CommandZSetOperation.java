package com.utils.redis.command;

import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * Interface ZSet Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:09:38
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandZSetOperation {
	/**
	 * SortedSet（有序集合） 实现命令：ZADD key score member，将一个 member元素及其 score值加入到有序集 key当中。如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
	 * 
	 * @author memory 2017年5月11日 下午12:04:09
	 * @param key
	 * @param score
	 *            值可以是整数值或双精度浮点数。
	 * @param member
	 * @return
	 */
	Boolean ZADD(String key, double score, String member);

	/**
	 * SortedSet（有序集合） 实现命令：ZADD key score member，将一个 member元素及其 score值加入到有序集 key当中。如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
	 * 
	 * @author memory 2017年6月1日 下午4:59:32
	 * @param key
	 * @param tuples
	 *            {@link TypedTuple} String value, Double score
	 * @return
	 */
	Long ZADD(String key, Set<TypedTuple<String>> tuples);

	/**
	 * SortedSet（有序集合） 实现命令：ZCARD key。返回有序集 key 的基数。
	 * 
	 * @author memory 2017年6月1日 下午5:06:17
	 * @param key
	 * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0 。
	 */
	Long ZCARD(String key);

	/**
	 * SortedSet（有序集合） 实现命令：ZCOUNT key min max。关于参数 min 和 max 的详细使用方法，请参考 {@link ZRANGEBYSCORE} 命令。
	 * 
	 * @author memory 2017年6月1日 下午5:17:47
	 * @param key
	 * @param min
	 * @param max
	 * @return 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
	 */
	Long ZCOUNT(String key, double min, double max);

	/**
	 * SortedSet（有序集合） 实现命令：ZINCRBY key increment member。为有序集 key 的成员 member 的 score 值加上增量 increment 。
	 * 
	 * <p>
	 * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
	 * </p>
	 * 
	 * <p>
	 * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 {@link ZADD} key increment member 。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午5:22:00
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	Double ZINCRBY(String key, double increment, String member);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGE key start stop，返回有序集 key中，指定区间内的成员。
	 * 
	 * <p>
	 * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 {@linkZREVRANGE} 命令。
	 * </p>
	 * 
	 * <p>
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 * </p>
	 * 
	 * <p>
	 * 超出范围的下标并不会引起错误。
	 * </p>
	 * 
	 * <p>
	 * 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将
	 * stop 当作最大下标来处理。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午12:04:42
	 * @param key
	 * @param start
	 * @param stop
	 * @return 其中成员的位置按 score 值递增(从小到大)来排序。
	 */
	Set<String> ZRANGE(String key, long start, long stop);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGE key start stop [WITHSCORES]，返回有序集 key中，指定区间内的成员。
	 * 
	 * <p>
	 * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 {@linkZREVRANGE} 命令。
	 * </p>
	 * 
	 * <p>
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 * </p>
	 * 
	 * <p>
	 * 超出范围的下标并不会引起错误。
	 * </p>
	 * 
	 * <p>
	 * 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将
	 * stop 当作最大下标来处理。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午12:04:42
	 * @param key
	 * @param start
	 * @param stop
	 * @return 其中成员的位置按 score 值递增(从小到大)来排序。
	 */
	Set<TypedTuple<String>> ZRANGEWITHSCORES(String key, long start, long stop);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGEBYSCORE key min max。
	 * 
	 * @author memory 2017年6月1日 下午5:47:32
	 * @param key
	 * @param min
	 * @param max
	 * @return 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 */
	Set<String> ZRANGEBYSCORE(String key, double min, double max);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGEBYSCORE key min max [LIMIT offset count]。
	 * 
	 * <p>
	 * LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N)
	 * 时间。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午6:00:12
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	Set<String> ZRANGEBYSCORE(String key, double min, double max, long offset, long count);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGEBYSCORE key min max [WITHSCORES]。
	 * 
	 * @author memory 2017年6月1日 下午5:47:32
	 * @param key
	 * @param min
	 * @param max
	 * @return 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 */
	Set<TypedTuple<String>> ZRANGEBYSCOREWITHSCORES(String key, double min, double max);

	/**
	 * SortedSet（有序集合） 实现命令：ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]。
	 * 
	 * <p>
	 * LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N)
	 * 时间。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午6:00:12
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 返回有序集 key
	 */
	Set<TypedTuple<String>> ZRANGEBYSCOREWITHSCORES(String key, double min, double max, long offset, long count);
}
