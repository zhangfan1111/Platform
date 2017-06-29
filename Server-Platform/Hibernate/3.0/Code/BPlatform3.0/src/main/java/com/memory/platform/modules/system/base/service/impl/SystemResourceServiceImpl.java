package com.memory.platform.modules.system.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.memory.platform.common.collect.EList;
import com.memory.platform.common.collect.ListFilter;
import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.hibernate4.criteria.HqlFilter;
import com.memory.platform.modules.system.base.dao.ISystemResourceDao;
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemResource;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemPrivilegeService;
import com.memory.platform.modules.system.base.service.ISystemResourceService;

@Service("systemResourceServiceImpl")
public class SystemResourceServiceImpl extends BaseServiceImpl<SystemResource> implements ISystemResourceService {

	@Autowired
	@Qualifier("systemResourceDaoImpl")
	private ISystemResourceDao systemResourceDao;

	@Autowired
	@Qualifier("systemPrivilegeServiceImpl")
	private ISystemPrivilegeService systemPrivilegeService;
	
	@Autowired
	@Qualifier("systemBasedataLinkServiceImpl")
	private ISystemBasedataLinkService systemBasedataLinkService;

	@Override
	public List<SystemResource> loadTreeGrid(HqlFilter hqlFilter) {
		List<SystemResource> l = new ArrayList<SystemResource>();
		String hql = "select distinct t from SystemResource t join t.syroles role join role.syusers user";
		final List<SystemResource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
		hql = "select distinct t from SystemResource t join t.syorganizations organization join organization.syusers user";
		final List<SystemResource> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_organization);
		l = new ArrayList<SystemResource>(new HashSet<SystemResource>(l));// 去重
		Collections.sort(l, new Comparator<SystemResource>() {// 排序
					@Override
					public int compare(SystemResource o1, SystemResource o2) {
						if (o1.getOrderCode() == null) {
							o1.setOrderCode(1000);
						}
						if (o2.getOrderCode() == null) {
							o2.setOrderCode(1000);
						}
						return o1.getOrderCode().compareTo(o2.getOrderCode());
					}
				});
		return l;
	}

	@Override
	public List<SystemResource> listResourcesByRoleIds(String[] roleIds) {
		List<SystemResource> masterResourceList = new ArrayList<SystemResource>();
		if(roleIds == null || roleIds.length < 1) {
			return masterResourceList;
		}
		
		masterResourceList = systemPrivilegeService.listAccessData(SystemResource.class, SystemRole.class, roleIds,"orderCode");
		List<SystemResource> result = filterList(masterResourceList);
		
		return result;
	}
	
	@Override
	public List<SystemResource> listResourcesByDeptIds(String[] deptIds) {
		List<SystemResource> masterResourceList = new ArrayList<SystemResource>();
		if(deptIds == null || deptIds.length < 1) {
			return masterResourceList;
		}
		
		masterResourceList = systemPrivilegeService.listAccessData(SystemResource.class, SystemDept.class, deptIds,"orderCode");
		
		List<SystemResource> result = filterList(masterResourceList);
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private List filterList(List list) {
		List<SystemResource> result = null;
		result = EList.filterList(list, new ListFilter<SystemResource, SystemResource>() {
			Map<String,SystemResource> map = Maps.newHashMap();
			
			List<SystemResource> result = EList.newArrayList();
			@Override
			public List<SystemResource> filter(List<SystemResource> inputList) {
				if(inputList == null) {
					return result;
				}
				for(SystemResource input : inputList) {
					if(input != null && !Strings.isNullOrEmpty(input.getId())) {
						if(map.get(input.getId()) == null) {
							map.put(input.getId(), input);
							result.add(input);
						}
					}
				}
				return result;
			}
			
		});
		return result;
	}

	@Override
	public List<SystemResource> listResourcesByUserId(String userId) {
		List<SystemRole> userRoleList = null;
		String[] userRoleIds;
		userRoleList = systemBasedataLinkService.listLinkData(SystemRole.class, SystemUser.class, userId);
		userRoleIds = new String[userRoleList.size()];
		for(int i=0;i<userRoleList.size();i++) {
			userRoleIds[i] = userRoleList.get(i).getId();
		}
		
		List<SystemDept> userDeptList = null;
		String[] userDeptIds;
		userDeptList = systemBasedataLinkService.listLinkData(SystemDept.class, SystemUser.class, userId);
		userDeptIds = new String[userDeptList.size()];
		for(int i=0;i<userDeptList.size();i++) {
			userDeptIds[i] = userDeptList.get(i).getId();
		}
		
		List<SystemResource> result1 = listResourcesByRoleIds(userRoleIds);
		List<SystemResource> result2 = listResourcesByDeptIds(userDeptIds);
		result1.addAll(result2);
		
		List<SystemResource> result = filterList(result1);
		
		return result;
	}

	@Override
	public List<SystemResource> listResourcesByUserRole(String userId) {
		List<SystemRole> userRoleList = null;
		String[] userRoleIds;
		userRoleList = systemBasedataLinkService.listLinkData(SystemRole.class, SystemUser.class, userId);
		userRoleIds = new String[userRoleList.size()];
		for(int i=0;i<userRoleList.size();i++) {
			userRoleIds[i] = userRoleList.get(i).getId();
		}
		
		return listResourcesByRoleIds(userRoleIds);
	}

	@Override
	public List<SystemResource> listResourcesByUserDept(String userId) {
		List<SystemDept> userDeptList = null;
		String[] userDeptIds;
		userDeptList = systemBasedataLinkService.listLinkData(SystemDept.class, SystemUser.class, userId);
		userDeptIds = new String[userDeptList.size()];
		for(int i=0;i<userDeptList.size();i++) {
			userDeptIds[i] = userDeptList.get(i).getId();
		}
		
		return listResourcesByDeptIds(userDeptIds);
	}
}
