package com.memory.platform.modules.system.base.service;

import java.util.List;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemDept;


public interface ISystemDeptService extends IBaseService<SystemDept> {


	public List<SystemDept> findAllChildrenByUserId(String userId);
}
