package com.memory.platform.modules.system.base.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memory.platform.hibernate4.dao.impl.BaseDaoImpl;
import com.memory.platform.modules.system.base.dao.ISystemUserDao;
import com.memory.platform.modules.system.base.model.SystemUser;

@Repository("systemUserDaoImpl")
public class SystemUserDaoImpl extends BaseDaoImpl<SystemUser> implements ISystemUserDao {

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
}
