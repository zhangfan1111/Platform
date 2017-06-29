package com.utils.redis.command;

import java.util.Collection;
import java.util.List;

/**
 * Interface List Command Operations.
 * <p>
 * http://doc.redisfans.com/
 * </p>
 * 
 * @author memory 2017年5月11日 下午9:05:10
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年5月11日
 * @modify by reason:{方法名}:{原因}
 */
public interface CommandListOperation {

	/**
	 * List（列表） 实现命令：BLPOP key [key ...] timeout。
	 * 
	 * <p>
	 * 非阻塞行为
	 * </p>
	 * <p>
	 * 当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字一起，组成结果返回给调用者。
	 * </p>
	 * <p>
	 * 阻塞行为
	 * </p>
	 * <p>
	 * 如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接，直到等待超时，或有另一个客户端对给定 key 的任意一个执行 LPUSH 或 RPUSH 命令为止。 超时参数 timeout
	 * 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午10:41:18
	 * @param key
	 * @param timeout
	 */
	void bLPop(String key, long timeout);

	/**
	 * List（列表） 实现命令：BRPOP key [key ...] timeout。
	 * 
	 * <p>
	 * BRPOP 是列表的阻塞式(blocking)弹出原语。
	 * </p>
	 * <p>
	 * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午10:45:53
	 * @param key
	 * @param timeout
	 */
	void bRPop(String key, long timeout);

	/**
	 * List（列表） 实现命令：BRPOPLPUSH source destination timeout。
	 * 
	 * <p>
	 * BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本，当给定列表 source 不为空时， BRPOPLPUSH 的表现和 RPOPLPUSH 一样。
	 * </p>
	 * 
	 * <p>
	 * 当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或 RPUSH 命令为止。
	 * </p>
	 * 
	 * <p>
	 * 超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午10:52:33
	 * @param sourceKey
	 *            源列表key
	 * @param destinationKey
	 *            目标列表key
	 * @param timeout
	 *            阻塞时间
	 */
	void bRPopLPush(String sourceKey, String destinationKey, long timeout);

	/**
	 * List（列表） 实现命令：LINDEX key index。返回列表 key 中，下标为 index 的元素。
	 * 
	 * <p>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * </p>
	 * <p>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:08:02
	 * @param key
	 * @param index
	 *            下标
	 * @return 列表中下标为 index 的元素。
	 */
	String lIndex(String key, long index);

	/**
	 * List（列表） 实现命令：LINSERT key BEFORE|AFTER pivot value。将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
	 * 
	 * <p>
	 * 当 pivot 不存在于列表 key 时，不执行任何操作。
	 * </p>
	 * <p>
	 * 当 key 不存在时， key 被视为空列表，不执行任何操作。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:27:28
	 * @param key
	 * @param position
	 *            BEFORE|AFTER
	 * @param pivot
	 *            目标值
	 * @param value
	 *            待插入值
	 * @return 返回插入操作完成之后，列表的长度。如果没有找到 pivot ，返回 -1 。
	 */
	Long lInsert(String key, String position, String pivot, String value);

	/**
	 * List（列表） 实现命令：LLEN key。
	 * 
	 * @author memory 2017年6月1日 上午11:33:16
	 * @param key
	 * @return 返回列表 key 的长度。如果 key 不存在，则 key 被解释为一个空列表，返回 0 。
	 */
	Long lLen(String key);

	/**
	 * List（列表） 实现命令：LPOP key，移除并返回列表 key的头元素
	 * 
	 * @author memory 2017年5月11日 下午12:02:05
	 * @param key
	 * @return 列表key的头元素
	 */
	String lPop(String key);

	/**
	 * List（列表） 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头。
	 * 
	 * <p>
	 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
	 * </p>
	 * 
	 * @author memory 2017年5月11日 下午12:01:41
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度
	 */
	Long lPush(String key, String value);

	/**
	 * List（列表） 实现命令：LPUSH key value，将多个值 value插入到列表 key的表头。
	 * 
	 * <p>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行
	 * LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:37:50
	 * @param key
	 * @param values
	 * @return 列表的长度
	 */
	Long lPush(String key, Collection<String> values);

	/**
	 * List（列表） 实现命令：LPUSH key value，将多个值 value插入到列表 key的表头。
	 * 
	 * <p>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行
	 * LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:37:50
	 * @param key
	 * @param values
	 * @return 列表的长度
	 */
	Long lPush(String key, String... values);

	/**
	 * List（列表） 实现命令：LPUSHX key value。将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
	 * 
	 * <p>
	 * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:41:59
	 * @param key
	 * @param value
	 * @return 表的长度
	 */
	Long lPushX(String key, String value);

	/**
	 * List（列表） 实现命令：LRANGE key start stop。返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 * 
	 * <p>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * </p>
	 * 
	 * <p>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * </p>
	 * 
	 * <p>
	 * 超出范围的下标值不会引起错误。
	 * </p>
	 * 
	 * <p>
	 * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，那么 LRANGE 返回一个空列表。
	 * </p>
	 * 
	 * <p>
	 * 如果 stop 下标比 end 下标还要大，Redis将 stop 的值设置为 end 。
	 * </p>
	 * 
	 * <p>
	 * 假如你有一个包含一百个元素的列表，对该列表执行 LRANGE list 0 10 ，结果是一个包含11个元素的列表
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:44:48
	 * @param key
	 * @param start
	 * @param stop
	 * @return 一个列表，包含指定区间内的元素。
	 */
	List<String> lRange(String key, long start, long stop);

	/**
	 * List（列表） 实现命令：LREM key count value。根据参数 count 的值，移除列表中与参数 value 相等的元素。
	 * 
	 * <p>
	 * count 的值可以是以下几种：
	 * </p>
	 * 
	 * <p>
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
	 * </p>
	 * 
	 * <p>
	 * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * </p>
	 * 
	 * <p>
	 * count = 0 : 移除表中所有与 value 相等的值
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:50:23
	 * @param key
	 * @param count
	 * @param value
	 * @return 被移除元素的数量。因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
	 */
	Long lRem(String key, long count, Object value);

	/**
	 * List（列表） 实现命令：LSET key index value。将列表 key 下标为 index 的元素的值设置为 value 。
	 * 
	 * @author memory 2017年6月1日 上午11:53:44
	 * @param key
	 * @param index
	 * @param value
	 */
	void lSet(String key, long index, String value);

	/**
	 * List（列表） 实现命令：LTRIM key start stop。对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
	 * 
	 * <p>
	 * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
	 * </p>
	 * 
	 * <p>
	 * 假如你有一个包含一百个元素的列表 list ，对该列表执行 LTRIM list 0 10 ，结果是一个包含11个元素的列表
	 * </p>
	 * 
	 * <p>
	 * LTRIM 命令通常和 LPUSH 命令或 RPUSH 命令配合使用，举个例子：LPUSH log newest_log 、 LTRIM log 0 99 模拟了一个日志程序，每次将最新日志 newest_log 放到 log
	 * 列表中，并且只保留最新的 100 项。
	 * </p>
	 * 
	 * <p>
	 * 超出范围的下标值不会引起错误。
	 * </p>
	 * 
	 * <p>
	 * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，或者 start > stop ， LTRIM 返回一个空列表(因为 LTRIM 已经将整个列表清空)。如果 stop 下标比 end
	 * 下标还要大，Redis将 stop 的值设置为 end 。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 上午11:55:51
	 * @param key
	 * @param start
	 * @param stop
	 */
	void lTrim(String key, long start, long stop);

	/**
	 * List（列表） 实现命令：RPOP key，移除并返回列表 key的尾元素
	 * 
	 * @author memory 2017年5月11日 下午12:02:51
	 * @param key
	 * @return 列表key的头元素
	 */
	String rPop(String key);

	/**
	 * List（列表） 实现命令：RPOPLPUSH source destination。
	 * 
	 * <p>
	 * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
	 * </p>
	 * 
	 * <p>
	 * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
	 * </p>
	 * 
	 * <p>
	 * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
	 * </p>
	 * 
	 * <p>
	 * 如果 source 和 destination 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊情况视作列表的旋转
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午1:34:16
	 * @param source
	 * @param destination
	 * @return 被弹出的元素
	 */
	String rPopLPush(String source, String destination);

	/**
	 * List（列表） 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)
	 * 
	 * @author memory 2017年5月11日 下午12:02:29
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度
	 */
	Long rPush(String key, String value);

	/**
	 * List（列表） 实现命令：RPUSHX key value。将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
	 * 
	 * <p>
	 * 和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做。
	 * </p>
	 * 
	 * @author memory 2017年6月1日 下午1:40:47
	 * @param key
	 * @param value
	 * @return 表的长度
	 */
	Long rPushX(String key, String value);
}
