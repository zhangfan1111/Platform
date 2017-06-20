package com.memory.platform.hibernate4.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memory.platform.hibernate4.dao.IExtendDao;

@Repository("extendDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExtendDaoImpl<T> implements IExtendDao<T>{

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public T getByHql(String hql, String... params) {
		Query q = getCurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
	    List l = q.list();
	    if (l == null || l.size() == 0) {
	      return null;
	    }
	    return (T) l.get(0);
	}

	@Override
	public List<T> find(String hql, String... params) {
		Query q = getCurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
	    return q.list();
	}

	@Override
	public List<T> find(String hql, int page, int rows, String... params) {
		Query q = getCurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql, String... params) {
		Query q = getCurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return (Long)q.uniqueResult();
	}

	@Override
	public int executeHql(String hql, String... params) {
		Query q = getCurrentSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.executeUpdate();
	}

	@Override
	public List<Map> findBySql(String sql, String... params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map> findBySql(String sql, int page, int rows, String... params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int executeSql(String sql, String... params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(String sql, String... params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public Map uniqueBySql(String sql) {
		List<Map> result = findBySql(sql);
		if(result == null || result.size() == 0)
			return null;
		else
			return result.get(0);
	}
	
	@Override
	public Map uniqueBySql(String sql, String... params) {
		List<Map> result = findBySql(sql, params);
		if(result == null || result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	@Override
	public Object mathBySql(String sql) {
		Query q = getCurrentSession().createSQLQuery(sql);
		return q.uniqueResult();
	}

	@Override
	public Object mathBySql(String sql, String... params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return (Object) q.uniqueResult();
	}

	
	
	
}
