package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface Value Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:09:24
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandValueOperation {
	/**
	 * String（字符串） 实现命令：APPEND key value。
	 * <p>
	 * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value
	 * 一样。
	 * </p>
	 * 
	 * @author memory 2017年5月15日 下午5:56:32
	 * @param key
	 * @param value
	 * @return 追加 value 之后， key 中字符串的长度。
	 */
	Integer append(String key, String value);

	/**
	 * String（字符串） 实现命令：DECR key。将 key 中储存的数字值减一。
	 * 
	 * <p>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 上午11:34:27
	 * @param key
	 * @return 执行之后， key 的值。
	 */
	Long decr(String key);

	/**
	 * String（字符串） 实现命令：DECRBY key decrement。将 key 中储存的数字值减decrement 。
	 * 
	 * <p>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 上午11:38:19
	 * @param key
	 * @param decrement
	 *            数字值减量
	 * @return 减去 decrement 之后， key 的值。
	 */
	Long decrBy(String key, long decrement);

	/**
	 * String（字符串） 实现命令，拓展命令：DECRBYFloat key decrement。将 key 中储存的数字值减decrement 。
	 * 
	 * <p>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 上午11:38:19
	 * @param key
	 * @param decrement
	 *            数字值减量
	 * @return 减去 decrement 之后， key 的值。
	 */
	Double decrByFloat(String key, double decrement);

	/**
	 * String（字符串） 实现命令：GET key。返回 key 所关联的字符串值。
	 * 
	 * @author memory 2017年5月16日 上午11:42:40
	 * @param key
	 * @return
	 */
	String redisGet(Object key);

	/**
	 * String（字符串） 实现命令：GETBIT key offset。对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
	 * 
	 * @author memory 2017年5月16日 上午11:47:15
	 * @param key
	 * @param offset
	 * @return 如果存在偏移量，则返回true；否则，返回false
	 */
	Boolean getBit(String key, long offset);

	/**
	 * String（字符串） 实现命令：GETRANGE key start end。
	 * 
	 * <p>
	 * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 上午11:49:44
	 * @param key
	 * @param start
	 * @param end
	 * @return 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。截取得出的子字符串。
	 */
	String getRange(String key, long start, long end);

	/**
	 * String（字符串） 实现命令：GETSET key value。
	 * 
	 * <p>
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 上午11:54:45
	 * @param key
	 * @param value
	 * @return 返回给定 key 的旧值。当 key 没有旧值时，也即是， key 不存在时，返回 null 。
	 */
	String getSet(String key, String value);

	/**
	 * String（字符串） 实现命令：INCR key。将 key 中储存的数字值增一。
	 * 
	 * <p>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 下午7:47:02
	 * @param key
	 * @return
	 */
	Long incr(String key);

	/**
	 * String（字符串） 实现命令：INCRBY key increment。将 key 所储存的值加上增量 increment 。
	 * <p>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 下午7:49:03
	 * @param key
	 * @param increment
	 * @return
	 */
	Long incrBy(String key, long increment);

	/**
	 * String（字符串） 实现命令：INCRBYFLOAT key increment。为 key 中所储存的值加上浮点数增量 increment。
	 * 
	 * <p>
	 * 如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0 ，再执行加法操作。
	 * </p>
	 * <p>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * </p>
	 * 
	 * @author memory 2017年5月16日 下午7:51:28
	 * @param key
	 * @param increment
	 * @return
	 */
	Double incrByFloat(String key, double increment);

	/**
	 * String（字符串） 实现命令：MGET key [key ...]。返回所有(一个或多个)给定 key 的值。
	 * 
	 * <p>
	 * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命令永不失败。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午2:16:58
	 * @param keys
	 * @return
	 */
	List<String> mGet(Collection<String> keys);

	/**
	 * String（字符串） 实现命令：MSET key value [key value ...]。同时设置一个或多个 key-value 对。
	 * 
	 * <p>
	 * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
	 * </p>
	 * <p>
	 * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午2:19:56
	 * @param map
	 */
	void mSet(Map<String, String> map);

	/**
	 * String（字符串） 实现命令：MSETNX key value [key value ...]，同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
	 * 
	 * <p>
	 * 即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午2:22:13
	 * @param map
	 * @return 当所有 key 都成功设置，返回 true；如果所有给定 key 都设置失败(至少有一个 key 已经存在)，那么返回false。
	 */
	Boolean mSetNX(Map<String, String> map);

	/**
	 * String（字符串） 实现命令：SET key value。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * 
	 * @author memory 2017年5月16日 上午11:56:14
	 * @param key
	 * @param value
	 * @return
	 */
	void redisSet(String key, String value);

	/**
	 * String（字符串） 实现命令：SETBIT key offset value。对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
	 * 
	 * <p>
	 * 位的设置或清除取决于 value 参数，可以是 0-false 也可以是 1-true
	 * </p>
	 * <p>
	 * 当 key 不存在时，自动生成一个新的字符串值。
	 * </p>
	 * <p>
	 * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
	 * </p>
	 * <p>
	 * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午2:57:56
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	Boolean setBit(String key, long offset, boolean value);

	/**
	 * String（字符串） 实现命令：SETEX key seconds value。将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
	 * 
	 * <p>
	 * 如果 key 已经存在， SETEX 命令将覆写旧值。
	 * </p>
	 * <p>
	 * 这个命令类似于以下两个命令：
	 * </p>
	 * <ul>
	 * <li>SET key value</li>
	 * <li>EXPIRE key seconds # 设置生存时间</li>
	 * </ul>
	 * 
	 * @author memory 2017年5月31日 下午3:13:45
	 * @param key
	 * @param value
	 * @param timeout
	 */
	void setEx(String key, long timeout, String value);

	/**
	 * String（字符串） 实现命令：SETNX key value。将 key 的值设为 value ，当且仅当 key 不存在。
	 * 
	 * <p>
	 * 若给定的 key 已经存在，则 SETNX 不做任何动作。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午3:19:37
	 * @param key
	 * @param value
	 */
	void setNx(String key, String value);

	/**
	 * String（字符串） 实现命令：SETRANGE key offset value。用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
	 * 
	 * <p>
	 * 不存在的 key 当作空白字符串处理。
	 * </p>
	 * 
	 * @author memory 2017年5月31日 下午3:19:41
	 * @param key
	 * @param offset
	 * @param value
	 */
	void setRange(String key, long offset, String value);

	/**
	 * String（字符串） 实现命令：STRLEN key。返回 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误。
	 * 
	 * @author memory 2017年5月31日 下午3:23:17
	 * @param key
	 * @return
	 */
	Long strLen(String key);
}
