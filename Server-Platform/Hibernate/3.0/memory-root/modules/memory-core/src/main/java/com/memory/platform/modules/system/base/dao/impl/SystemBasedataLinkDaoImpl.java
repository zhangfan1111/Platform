package com.memory.platform.modules.system.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.memory.platform.hibernate4.dao.impl.BaseDaoImpl;
import com.memory.platform.modules.system.base.dao.ISystemBasedataLinkDao;
import com.memory.platform.modules.system.base.model.SystemBasedataLink;

@Repository("systemBasedataLinkDaoImpl")
public class SystemBasedataLinkDaoImpl extends BaseDaoImpl<SystemBasedataLink> implements ISystemBasedataLinkDao {


	@Override
	public <T> List<T> listLinkData(Class<T> baseData, String hql) {
		List<T> list;
		Query q = getCurrentSession().createQuery(hql);
		list = q.list();
		if(list == null) {
			return new ArrayList();
		}
		return list;
	}
}
