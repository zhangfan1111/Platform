package com.utils.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.query.SortQuery;

/**
 * Interface Key Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:05:38
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandRedisOperation {

	/**
	 * Key（键），简单的key-value操作 实现命令：DEL key，删除一个key。不存在的 key 会被忽略。
	 * 
	 * @author memory 2017年5月11日 上午11:27:40
	 * @param key
	 */
	void del(String key);

	/**
	 * Key（键），简单的key-value操作 实现命令：DEL [key ...]，删除多个key。不存在的 key 会被忽略。
	 * 
	 * @author memory 2017年5月12日 上午10:41:20
	 * @param keys
	 */
	void del(Collection<String> keys);

	/**
	 * Key（键），简单的key-value操作 实现命令：DUMP key。序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
	 * 
	 * <p>
	 * 序列化生成的值有以下几个特点： 它带有 64 位的校验和，用于检测错误， RESTORE 在进行反序列化之前会先检查校验和。 值的编码格式和 RDB 文件保持一致。 RDB 版本会被编码在序列化值当中，如果因为 Redis
	 * 的版本不同造成 RDB 格式不兼容，那么 Redis 会拒绝对这个值进行反序列化操作。 序列化的值不包括任何生存时间信息。
	 * </p>
	 * 
	 * @author memory 2017年5月12日 上午10:46:03
	 * @param key
	 * @return 如果 key 不存在，那么返回 nil 。否则，返回序列化之后的值。
	 */
	byte[] dump(String key);

	/**
	 * Key（键），简单的key-value操作 实现命令：EXISTS key。检查给定 key 是否存在。
	 * 
	 * @author memory 2017年5月12日 上午10:55:04
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：EXPIRE key seconds。为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * 
	 * @author memory 2017年5月12日 上午11:04:20
	 * @param key
	 * @param timeout
	 * @return 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 false 。
	 */
	Boolean expire(String key, long timeout);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：EXPIREAT key timestamp。
	 * 
	 * <p>EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。</p>
	 * 
	 * @author memory 2017年5月12日 上午11:06:59
	 * @param key
	 * @param timestamp  单位：seconds，UNIX 时间戳(unix timestamp)，从格林威治时间1970年01月01日00时00分00秒起至现在的总秒数
	 * @return 当 key 不存在或没办法设置生存时间，返回false
	 */
	Boolean expireAt(String key, long timestamp);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key。
	 * 
	 * <p>KEYS * 匹配数据库中所有 key 。</p>
	 * <p>KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。</p>
	 * <p>KEYS h*llo 匹配 hllo 和 heeeeello 等。</p>
	 * <p>KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。</p>
	 * 
	 * @author memory 2017年5月11日 上午11:27:06
	 * @param pattern
	 * @return
	 */
	Set<String> keys(String pattern);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：MOVE key db。将当前数据库的 key 移动到给定的数据库 db 当中。
	 * 
	 * <p>如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。</p>
	 * 
	 * @author memory 2017年5月12日 上午11:31:14
	 * @param key
	 * @param db
	 * @return
	 */
	Boolean move(String key, int db);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：PERSIST key。
	 * 
	 * <p>移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。</p>
	 * 
	 * @author memory 2017年5月12日 上午11:47:29
	 * @param key
	 * @return 如果 key 不存在或 key 没有设置生存时间，返回false。
	 */
	Boolean persist(String key);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：PEXPIRE key milliseconds。
	 * 
	 * <p>这个命令和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像 EXPIRE 命令那样，以秒为单位。</p>
	 * 
	 * @author memory 2017年5月12日 上午11:51:37
	 * @param key
	 * @param milliseconds 毫秒
	 * @return
	 */
	Boolean pExpire(String key, long milliseconds);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：PEXPIREAT key milliseconds-timestamp。
	 * 
	 * <p>这个命令和 EXPIREAT 命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳，而不是像 EXPIREAT 那样，以秒为单位。</p>
	 * 
	 * @author memory 2017年5月12日 下午12:06:33
	 * @param key
	 * @param millisecondsTimestamp 单位：milliseconds，UNIX 时间戳(unix timestamp)，从格林威治时间1970年01月01日00时00分00秒起至现在的总毫秒数。
	 * @return
	 */
	Boolean pExpireAt(String key, long millisecondsTimestamp);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：TTL key，以millseconds为单位，返回给定 key的剩余生存时间(TTL, time to live)
	 * 
	 * @author memory 2017年5月12日 上午11:40:00
	 * @param key
	 * @return 返回毫秒millseconds
	 */
	Long pTTL(String key);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：RANDOMKEY。从当前数据库中随机返回(不删除)一个 key 。
	 * 
	 * @author memory 2017年5月12日 下午1:29:40
	 * @return当数据库为空时，返回 nil 。
	 */
	String randomKey();
	
	/**
	 * Key（键），简单的key-value操作 实现命令：RENAME key newkey。将 key 改名为 newkey 。
	 * 
	 * <p>当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。当 newkey 已经存在时， RENAME 命令将覆盖旧值。</p>
	 * 
	 * @author memory 2017年5月12日 下午1:32:44
	 * @param oldKey
	 * @param newKey
	 */
	void rename(String oldKey, String newKey);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：RENAMENX key newkey。当且仅当 newkey 不存在时，将 key 改名为 newkey 。当 key 不存在时，返回一个错误。
	 * @author memory 2017年5月12日 下午1:35:14
	 * @param oldKey
	 * @param newKey
	 * @return 如果 newkey 已经存在，返回false
	 */
	Boolean renameNX(String oldKey, String newKey);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：RESTORE key ttl serialized-value。反序列化给定的序列化值，并将它和给定的 key 关联。
	 * 
	 * @author memory 2017年5月12日 下午1:39:51
	 * @param key
	 * @param timeToLive 参数 ttl 以毫秒为单位为 key 设置生存时间；如果 ttl 为 0 ，那么不设置生存时间。
	 * @param serializedValue 序列化值
	 */
	void sort(String key, long timeToLive, byte[] serializedValue);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]。
	 * 
	 * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
	 * 
	 * @author memory 2017年5月12日 下午5:57:35
	 * @param sortQuery
	 * @return 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。
	 */
	List<String> sort(SortQuery<String> sortQuery);
	
	
	/**
	 * Key（键），简单的key-value操作 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)
	 * 
	 * @author memory 2017年5月11日 上午11:26:27
	 * @param key
	 * @return 返回秒seconds
	 */
	Long ttl(String key);
	
	/**
	 * Key（键），简单的key-value操作 实现命令：TYPE key。返回 key 所储存的值的类型。
	 * @author memory 2017年5月15日 下午4:14:08
	 * @param key
	 * @return null (key不存在)、string (字符串)、list (列表)、set (集合)、zset (有序集)、hash (哈希表)
	 */
	String redisType(String key);
}
