package com.memory.platform.modules.system.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.modules.system.base.dao.ISystemDictDao;
import com.memory.platform.modules.system.base.model.SystemDict;
import com.memory.platform.modules.system.base.service.ISystemDictService;

@Service("systemDictServiceImpl")
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDict> implements ISystemDictService {

	@Autowired
	@Qualifier("systemDictDaoImpl")
	private ISystemDictDao SystemDictDao;

	
}
