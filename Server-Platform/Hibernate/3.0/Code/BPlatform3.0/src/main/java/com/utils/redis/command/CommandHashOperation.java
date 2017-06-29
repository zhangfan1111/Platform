package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

/**
 * Interface Hash Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:04:27
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandHashOperation {
	/**
	 * Hash（哈希表） 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
	 * 
	 * @author memory 2017年5月11日 下午12:00:54
	 * @param key
	 * @param fields
	 */
	void hDel(String key, Object... fields);

	/**
	 * Hash（哈希表） 实现命令：HEXISTS key field 查看哈希表 key 中，给定域 field 是否存在
	 * 
	 * @author memory 2017年5月11日 下午2:15:38
	 * @param key
	 * @param field
	 * @return
	 */
	boolean hExists(String key, Object field);

	/**
	 * Hash（哈希表） 实现命令：HGET key field，返回哈希表 key中给定域 field的值
	 * 
	 * @author memory 2017年5月11日 下午12:00:33
	 * @param key
	 * @param field
	 * @return
	 */
	String hGet(String key, Object field);

	/**
	 * Hash（哈希表） 实现命令：HGETALL key，返回哈希表 key中，所有的域和值
	 * 
	 * @author memory 2017年5月11日 下午12:01:20
	 * @param key
	 * @return
	 */
	Map<Object, Object> hGetAll(String key);

	/**
	 * Hash（哈希表） 实现命令：HINCRBY key field increment.为哈希表 key 中的域 field 的值加上增量 increment 。
	 * <p>
	 * 增量也可以为负数，相当于对给定域进行减法操作。 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。 对一个储存字符串值的域
	 * field 执行 HINCRBY 命令将造成一个错误。 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 * </p>
	 * @author memory 2017年5月11日 下午5:37:44
	 * @param key
	 * @param hashKey
	 * @param increment
	 * @return
	 */
	Long hIncrBy(String key, Object hashKey, long increment);

	/**
	 * Hash（哈希表） 实现命令：HINCRBYFLOAT key field increment.为哈希表 key 中的域 field 加上浮点数增量 increment 。
	 * <p>
	 * 如果哈希表中没有域 field ，那么 HINCRBYFLOAT 会先将域 field 的值设为 0 ，然后再执行加法操作。 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域 field
	 * ，最后再执行加法操作。 当以下任意一个条件发生时，返回一个错误： 域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型） 域 field 当前的值或给定的增量
	 * increment 不能解释(parse)为双精度浮点数(double precision floating point number) HINCRBYFLOAT 命令的详细功能和 INCRBYFLOAT 命令类似。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午6:03:37
	 * @param key
	 * @param hashKey
	 * @param increment
	 * @return
	 */
	Double hIncrByFloat(String key, Object hashKey, double increment);

	/**
	 * Hash（哈希表） 实现命令：HKEYS key.返回哈希表 key 中的所有域。
	 * 
	 * @author memory 2017年5月11日 下午6:09:09
	 * @param key
	 * @return 当 key 不存在时，返回一个空表。
	 */
	Set<Object> hKeys(String key);

	/**
	 * Hash（哈希表） 实现命令：HLEN key.返回哈希表 key 中域的数量。
	 * 
	 * @author memory 2017年5月11日 下午6:12:26
	 * @param key
	 * @return 当 key 不存在时，返回 0 。
	 */
	Long hLen(String key);

	/**
	 * Hash（哈希表） 实现命令：HMGET key field [field ...].返回哈希表 key 中，一个或多个给定域的值。
	 * 
	 * <p>
	 * 如果给定的域不存在于哈希表，那么返回一个 nil 值。 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午6:17:38
	 * @param key
	 * @param hashKeys
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	List<Object> hmGet(String key, Collection<Object> hashKeys);

	/**
	 * Hash（哈希表） 实现命令：HMSET key field value [field value ...].
	 * 
	 * <p>
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。此命令会覆盖哈希表中已存在的域。如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午6:31:14
	 * @param key
	 * @param map
	 */
	void hmSet(String key, Map<Object, Object> map);

	/**
	 * Hash（哈希表） 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
	 * 
	 * @author memory 2017年5月11日 下午12:00:10
	 * @param key
	 * @param field
	 * @param value
	 */
	void hSet(String key, Object field, Object value);

	/**
	 * Hash（哈希表） 实现命令：HSETNX key field value，将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
	 * 
	 * <p>
	 * 若域 field 已经存在，该操作无效。 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午2:06:24
	 * @param key
	 * @param field
	 * @param value
	 */
	boolean hSetNX(String key, Object field, Object value);

	/**
	 * Hash（哈希表） 实现命令：HVALS key.返回哈希表 key 中所有域的值。
	 * 
	 * @author memory 2017年5月11日 下午6:42:37
	 * @param key
	 * @return 当 key 不存在时，返回一个空表。
	 */
	List<Object> hVals(String key);

	/**
	 * Hash（哈希表） 实现命令：HSCAN key cursor [MATCH pattern] [COUNT count] 具体信息请参考 SCAN 命令。
	 * 
	 * @author memory 2017年5月11日 下午8:10:51
	 * @param key
	 * @param options
	 *            ScanOptions:Long count匹配长度；String pattern匹配值。
	 * @return 返回对象游标
	 */
	Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options);
}
