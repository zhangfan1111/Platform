package com.memory.platform.modules.system.base.service;

import java.util.List;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemRole;


public interface ISystemRoleService extends IBaseService<SystemRole> {
	public List<SystemRole> listRolesByUserId(String userId);

}
