package com.memory.platform.core.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.hibernate4.criteria.HqlFilter;
import com.memory.platform.hibernate4.dao.IBaseDao;
import com.memory.platform.hibernate4.search.Search;
import com.memory.platform.hibernate4.search.SearchResult;


/**
 * 基础业务逻辑
 * 
 * @author 
 * 
 * @param <T>
 */
@Service("baseServiceImpl")
public class BaseServiceImpl<T> implements IBaseService<T> {

	@Autowired
	@Qualifier("baseDaoImpl")
	private IBaseDao<T> baseDao;

	@Override
	public Serializable save(T o) {
		return baseDao.save(o);
	}

	@Override
	public void delete(T o) {
		baseDao.delete(o);
	}

	@Override
	public void update(T o) {
		baseDao.update(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		baseDao.saveOrUpdate(o);
	}

	@Override
	public T getById(Serializable id) {
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return baseDao.getById(c, id);
	}

	@Override
	public T getByHql(String hql) {
		return baseDao.getByHql(hql);
	}

	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		return baseDao.getByHql(hql, params);
	}

	@Override
	public T getByFilter(HqlFilter hqlFilter) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select distinct t from " + className + " t";
		return getByHql(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public List<T> find() {
		return findByFilter(new HqlFilter());
	}

	@Override
	public List<T> find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> findByFilter(HqlFilter hqlFilter) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		return baseDao.find(hql, page, rows);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}

	@Override
	public List<T> find(int page, int rows) {
		return findByFilter(new HqlFilter(), page, rows);
	}

	@Override
	public List<T> findByFilter(HqlFilter hqlFilter, int page, int rows) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
	}

	@Override
	public Long count(String hql) {
		return baseDao.count(hql);
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		return baseDao.count(hql, params);
	}

	@Override
	public Long countByFilter(HqlFilter hqlFilter) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select count(distinct t) from " + className + " t";
		return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

	@Override
	public Long count() {
		return countByFilter(new HqlFilter());
	}

	@Override
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}

	@Override
	public List findBySql(String sql) {
		return baseDao.findBySql(sql);
	}

	@Override
	public List findBySql(String sql, int page, int rows) {
		return baseDao.findBySql(sql, page, rows);
	}

	@Override
	public List findBySql(String sql, Map<String, Object> params) {
		return baseDao.findBySql(sql, params);
	}

	@Override
	public List findBySql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.findBySql(sql, params, page, rows);
	}

	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);
	}

	@Override
	public BigInteger countBySql(String sql) {
		return baseDao.countBySql(sql);
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		return baseDao.countBySql(sql, params);
	}

	@Override
	public <T> SearchResult<T> searchByHql(Search<T> search) {
		return baseDao.searchByHql(search);
	}

	@Override
	public <T> SearchResult<T> searchBySql(Search<T> search) {
		return baseDao.searchBySql(search);
	}

	@Override
	public T getByHql(String hql, String... params) {
		return baseDao.getByHql(hql, params);
	}

	@Override
	public List<T> find(String hql, String... params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, int page, int rows, String... params) {
		return baseDao.find(hql, page, rows, params);
	}

	@Override
	public Long count(String hql, String... params) {
		return baseDao.count(hql, params);
	}

	@Override
	public int executeHql(String hql, String... params) {
		return baseDao.executeHql(hql, params);
	}

	@Override
	public List<Map> findBySql(String sql, String... params) {
		return baseDao.findBySql(sql, params);
	}

	@Override
	public List<Map> findBySql(String sql, int page, int rows, String... params) {
		return baseDao.findBySql(sql, page, rows, params);
	}

	@Override
	public int executeSql(String sql, String... params) {
		return baseDao.executeSql(sql, params);
	}

	@Override
	public BigInteger countBySql(String sql, String... params) {
		return baseDao.countBySql(sql, params);
	}

	@Override
	public Map uniqueBySql(String sql) {
		return baseDao.uniqueBySql(sql);
	}

	@Override
	public Map uniqueBySql(String sql, String... params) {
		return baseDao.uniqueBySql(sql, params);
	}

	@Override
	public BigDecimal mathBySql(String sql) {
		return baseDao.mathBySql(sql);
	}

	@Override
	public BigDecimal mathBySql(String sql, String... params) {
		return baseDao.mathBySql(sql, params);
	}

	@Override
	public Long mathByHql(String hql) {
		return baseDao.mathByHql(hql);
	}

	@Override
	public Long mathByHql(String hql, String... params) {
		return baseDao.mathByHql(hql, params);
	}
	
	
}
