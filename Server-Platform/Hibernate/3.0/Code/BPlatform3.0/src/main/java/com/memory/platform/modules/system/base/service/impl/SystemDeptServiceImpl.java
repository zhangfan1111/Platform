package com.memory.platform.modules.system.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.memory.platform.common.collect.BaseListFilter;
import com.memory.platform.common.collect.EList;
import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.modules.system.base.dao.ISystemDeptDao;
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.service.ISystemDeptService;
import com.memory.platform.modules.system.base.service.ISystemUserService;

@Service("systemDeptServiceImpl")
public class SystemDeptServiceImpl extends BaseServiceImpl<SystemDept> implements ISystemDeptService {

	@Autowired
	@Qualifier("systemDeptDaoImpl")
	private ISystemDeptDao systemDeptDao;
	
	@Autowired
	@Qualifier("systemUserServiceImpl")
	private ISystemUserService systemUserService;

	@Override
	/**
	 * 根据userId查询所有该用户所在部门的父部门集合
	 * @param userId
	 * @return
	 */
	public List<SystemDept> findAllChildrenByUserId(String userId) {
		List<SystemDept> allSystemDepts = EList.newArrayList();
		List<SystemDept> systemDepts = systemUserService.listUserDepts(userId);
		for (SystemDept systemDept : systemDepts) {
			allSystemDepts.addAll(findAllChildrenByDeptId(systemDept.getCode()));
		}
		
		List<SystemDept> filteredDepts = EList.filterList(allSystemDepts, new BaseListFilter<SystemDept>() {
			@Override
			public Object filterFieldValue(SystemDept input) {
				String id = input.getId();
				return id;
			}
		});
		return filteredDepts;
	}

	/**
	 * 根据code查询所有子节点
	 * @param code
	 * @return
	 */
	public List<SystemDept> findAllChildrenByDeptId(String code){
		Preconditions.checkArgument(!Strings.isNullOrEmpty(code), "传入的部门id不能为空");
		List<SystemDept> systemDepts = systemDeptDao.find("FROM SystemDept s WHERE s.code LIKE '" + code + "%'");
		return systemDepts;
	}
	
}
