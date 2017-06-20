package com.memory.platform.core.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.IExtendService;
import com.memory.platform.hibernate4.dao.IExtendDao;

@Service("extendService")
@SuppressWarnings("rawtypes")
public class ExtendServiceImpl<T> implements IExtendService<T>{

	@Autowired
	@Qualifier("extendDao")
	private IExtendDao<T> extendDao;
	
	@Override
	public T getByHql(String hql, String... params) {
		return extendDao.getByHql(hql, params);
	}

	@Override
	public List<T> find(String hql, String... params) {
		return extendDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, int page, int rows, String... params) {
		return extendDao.find(hql, page, rows, params);
	}

	@Override
	public Long count(String hql, String... params) {
		return extendDao.count(hql, params);
	}

	@Override
	public int executeHql(String hql, String... params) {
		return extendDao.executeHql(hql, params);
	}

	@Override
	public List<Map> findBySql(String sql, String... params) {
		return extendDao.findBySql(sql, params);
	}

	@Override
	public List<Map> findBySql(String sql, int page, int rows, String... params) {
		return extendDao.findBySql(sql, page, rows, params);
	}

	@Override
	public int executeSql(String sql, String... params) {
		return extendDao.executeSql(sql, params);
	}

	@Override
	public BigInteger countBySql(String sql, String... params) {
		return extendDao.countBySql(sql, params);
	}

	@Override
	public Map uniqueBySql(String sql) {
		return extendDao.uniqueBySql(sql);
	}

	@Override
	public Map uniqueBySql(String sql, String... params) {
		return extendDao.uniqueBySql(sql, params);
	}
	
	
	
}
