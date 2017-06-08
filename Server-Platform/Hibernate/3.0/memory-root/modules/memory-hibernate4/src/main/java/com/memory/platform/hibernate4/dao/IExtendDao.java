package com.memory.platform.hibernate4.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.memory.platform.hibernate4.dao.IBaseDao;

@SuppressWarnings("rawtypes")
public interface IExtendDao<T> extends IBaseDao<T> {
	
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
	public abstract Object mathBySql(String sql);
	
	/**
	 * 根据jdbc语句计算值	例如sum  max  min等
	 * 使用方式 ==>>  select max(money) from xxx_user where name=?
	 * @author yangshaoping 2017年6月7日 下午2:47:51
	 * @param sql
	 * @param params	"?"代表的具体值 ，个数不定 。如果值为整形、浮点型等可不考虑sql注入
	 * @return
	 */
	public abstract Object mathBySql(String sql, String... params);
	
}
