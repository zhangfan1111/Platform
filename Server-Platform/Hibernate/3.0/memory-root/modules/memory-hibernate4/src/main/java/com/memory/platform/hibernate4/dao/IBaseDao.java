package com.memory.platform.hibernate4.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.memory.platform.hibernate4.search.Search;
import com.memory.platform.hibernate4.search.SearchResult;

/**
 * 基础数据库操作类
 * 
 * 其他DAO继承此类获取常用的数据库操作方法，基本上你能用到的方法这里都有了，不需要自己建立DAO了。
 * 
 * @author 
 * 
 * @param <T>
 *            模型
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao<T> {

	public Session getCurrentSession();
	
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);

	/**
	 * 保存或更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 通过主键获得对象
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T getById(Class<T> c, Serializable id);
	
	/**
	 * 通过主键获得对象
	 * 
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T getById(Serializable id);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	public T getByHql(String hql);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getByHql(String hql, Map<String, Object> params);

	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	public List<T> find(String hql);

	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, int page, int rows);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T)
	 * @return long
	 */
	public Long count(String hql);

	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T where xx = :xx)
	 * @param params
	 *            参数
	 * @return long
	 */
	public Long count(String hql, Map<String, Object> params);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 通过sql获取单个对象
	 * @param sql
	 * @return
	 */
	public Map getBySql(String sql);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params);

	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 数目
	 */
	public BigInteger countBySql(String sql);

	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public BigInteger countBySql(String sql, Map<String, Object> params);

	public <T> SearchResult<T> searchByHql(Search<T> search);
	public <T> SearchResult<T> searchBySql(Search<T> search);
	
	/**
	 * 根据hql语句获取单个对象 
	 * 使用方式  ==>>  from User where userId=?  
	 * @author yangshaoping 2017年6月7日 下午2:24:01
	 * @param hql
	 * @param params "?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract T getByHql(String hql, String... params);
	
	/**
	 * 根据hql语句获取多个对象
	 * 使用方式 ==>>  from User where name=?
	 * @author yangshaoping 2017年6月7日 下午2:26:45
	 * @param hql	
	 * @param params "?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract List<T> find(String hql, String... params);
	
	/**
	 * 根据hql语句获取多个对象
	 * 使用方式 ==>>  from User where name=?
	 * @author yangshaoping 2017年6月7日 下午2:32:19
	 * @param hql
	 * @param page	页数，从1开始
	 * @param rows	每页条数
	 * @param params  "?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract List<T> find(String hql, int page, int rows, String... params);
	
	/**
	 * 根据hql语句统计数量
	 * 使用方式 ==>>  select count(userId) from User where name=?
	 * @author yangshaoping 2017年6月7日 下午2:35:15
	 * @param hql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract Long count(String hql, String... params);
	
	/**
	 * 执行hql语句	限增删改
	 * 使用方式 ==>>  delete from User where name=?
	 * @author yangshaoping 2017年6月7日 下午2:36:22
	 * @param hql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract int executeHql(String hql, String... params);
	
	/**
	 * 根据jdbc语句获取多条数据	
	 * 使用方式 ==>>  select * from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:39:57
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract List<Map> findBySql(String sql, String... params);
	
	/**
	 * 根据jdbc语句获取多条数据	
	 * 使用方式 ==>>  select * from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:42:21
	 * @param sql	
	 * @param page	页数，从1开始
	 * @param rows	每页条数
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract List<Map> findBySql(String sql, int page, int rows, String... params);
	
	/**
	 * 执行jdbc语句	限增删改
	 * 使用方式 ==>>  delete from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:43:11
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return	
	 */
	public abstract int executeSql(String sql, String... params);
	
	/**
	 * 根据jdbc语句统计数量
	 * 使用方式 ==>>  select count(user_id) from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:43:38
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract BigInteger countBySql(String sql, String... params);
	
	/**
	 * 根据jdbc语句获取单条数据
	 * 使用方式 ==>>  select * from xxx_user
	 * @author yangshaoping 2017年6月7日 下午2:44:01
	 * @param sql
	 * @return
	 */
	public abstract Map uniqueBySql(String sql);
	
	/**
	 * 根据jdbc语句获取单条数据
	 * 使用方式 ==>>  select * from xxx_user where user_id=?
	 * @author yangshaoping 2017年6月7日 下午2:44:46
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract Map uniqueBySql(String sql, String... params);
	
	/**
	 * 根据jdbc语句计算值	例如sum  max  min等
	 * 使用方式 ==>>  select sum(money) from xxx_user
	 * @author yangshaoping 2017年6月7日 下午2:47:51
	 * @param sql
	 * @return
	 */
	public abstract BigDecimal mathBySql(String sql);
	
	/**
	 * 根据jdbc语句计算值	例如sum  max  min等
	 * 使用方式 ==>>  select max(money) from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:47:51
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract BigDecimal mathBySql(String sql, String... params);
	
	/**
	 * 根据hql语句计算值	 例如sum  max  min等
	 * 使用方式 ==>>  select sum(money) from User
	 * @author yangshaoping 2017年6月7日 下午2:47:51
	 * @param hql
	 * @return
	 */
	public abstract Long mathByHql(String hql);
	
	/**
	 * 根据hql语句计算值	例如sum  max  min等
	 * 使用方式 ==>>  select max(money) from User where name=?
	 * @author yangshaoping 2017年6月7日 下午2:47:51
	 * @param hql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract Long mathByHql(String hql, String... params);
}
