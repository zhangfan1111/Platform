package com.memory.platform.modules.system.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.hibernate4.dao.IBaseDao;
import com.memory.platform.modules.system.base.dao.ISystemPrivilegeDao;
import com.memory.platform.modules.system.base.datalink.PrivilegeHelper;
import com.memory.platform.modules.system.base.model.SystemPrivilege;
import com.memory.platform.modules.system.base.service.ISystemPrivilegeService;

@Service("systemPrivilegeServiceImpl")
public class SystemPrivilegeServiceImpl extends BaseServiceImpl<SystemPrivilege> implements ISystemPrivilegeService {

	@Autowired
	@Qualifier("systemPrivilegeDaoImpl")
	private ISystemPrivilegeDao systemPrivilegeDao;

	@Autowired
	@Qualifier("baseDaoImpl")
	private IBaseDao baseDao;
	
	@Override
	public <T> List<T> listAccessData(Class<T> accessClass,
			Class<?> masterClass, String masterValue) {
		
		List<T> list;
		
		String accessDataTable = accessClass.getName();
		
		String accessClassDbFlag = PrivilegeHelper.getDbTypeFlag(accessClass);
		String masterClassDbFlag = PrivilegeHelper.getDbTypeFlag(masterClass);
		String hql = "select b From "+accessDataTable+" b, com.memory.platform.modules.system.base.model.SystemPrivilege p where p.master='"+masterClassDbFlag+"' and p.masterValue='"+masterValue+"' and p.access='"+accessClassDbFlag+"' and p.accessValue=b.id";
		
		Query q = baseDao.getCurrentSession().createQuery(hql);
		list = q.list();
		
		return list;
		
	}
	
	@Override
	public <T> List<T> listAccessData(Class<T> accessClass,
			Class<?> masterClass, String[] masterValues,String accessClassOrderFilde) {
		
		StringBuilder sb = new StringBuilder();
		if(masterValues != null && masterValues.length>0) {
			sb.append("(");
			for(int i=0;i<masterValues.length;i++) {
				if(i<masterValues.length-1) {
					sb.append("'").append(masterValues[i]).append("',");
				} else {
					sb.append("'").append(masterValues[i]).append("'");
				}
			}
			sb.append(")");
			
			List<T> list;
			
			String accessDataTable = accessClass.getName();
			
			String accessClassDbFlag = PrivilegeHelper.getDbTypeFlag(accessClass);
			String masterClassDbFlag = PrivilegeHelper.getDbTypeFlag(masterClass);
			String hql = "select b From "+accessDataTable+" b, com.memory.platform.modules.system.base.model.SystemPrivilege p where p.master='"+masterClassDbFlag+"' and p.masterValue in "+sb.toString()+" and p.access='"+accessClassDbFlag+"' and p.accessValue=b.id order by b."+accessClassOrderFilde;
			
			Query q = baseDao.getCurrentSession().createQuery(hql);
			list = q.list();
			
			return list;
		}
		return null;
	}

	@Override
	public void add(Class<?> masterClass, String masterValue,
			Class<?> accessClass, List<String> accessIds) {
		String masterClassDbFlag = PrivilegeHelper.getDbTypeFlag(masterClass);
		String accessClassDbFlag = PrivilegeHelper.getDbTypeFlag(accessClass);
		
		for(String accessValue : accessIds) {
			SystemPrivilege sp = new SystemPrivilege();
			sp.setMaster(masterClassDbFlag);
			sp.setMasterValue(masterValue);
			sp.setAccess(accessClassDbFlag);
			sp.setAccessValue(accessValue);
			sp.setOperation("0");
			this.save(sp);
		}
	}

	@Override
	public void delete(Class<?> masterClass, String masterValue,
			Class<?> accessClass, List<String> accessIds) {
		String masterClassDbFlag = PrivilegeHelper.getDbTypeFlag(masterClass);
		String accessClassDbFlag = PrivilegeHelper.getDbTypeFlag(accessClass);
		
		String hql = "delete from SystemPrivilege l where l.master=:master and l.masterValue=:masterValue and l.access=:access and l.accessValue=:accessValue";
		Map<String,Object> params = new HashMap<String,Object>();
		for(String accessValue : accessIds) {
			params.put("master", masterClassDbFlag);
			params.put("masterValue", masterValue);
			params.put("access", accessClassDbFlag);
			params.put("accessValue", accessValue);
			
			systemPrivilegeDao.executeHql(hql,params);
			
			params.clear();
		}
	}

}
