package com.memory.platform.core.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.IExtendService;
import com.memory.platform.hibernate4.dao.IExtendDao;

@Service("extendService")
@SuppressWarnings("rawtypes")
public class ExtendServiceImpl<T> extends BaseServiceImpl<T> implements IExtendService<T>{

	@Autowired
	@Qualifier("extendDao")
	private IExtendDao<T> extendDao;
	
	
}
