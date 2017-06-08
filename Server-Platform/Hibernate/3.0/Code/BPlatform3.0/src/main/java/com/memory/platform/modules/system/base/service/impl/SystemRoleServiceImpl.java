package com.memory.platform.modules.system.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.modules.system.base.dao.ISystemRoleDao;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.service.ISystemRoleService;

@Service("systemRoleServiceImpl")
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements ISystemRoleService {

	@Autowired
	@Qualifier("systemRoleDaoImpl")
	private ISystemRoleDao systemRoleDao;

	public List<SystemRole> listRolesByUserId(String userId) {
		List<SystemRole> roleList = new ArrayList<SystemRole>();
		
		String hql = "From SystemRole r where r.";
		
		return null;
	}
	
}
