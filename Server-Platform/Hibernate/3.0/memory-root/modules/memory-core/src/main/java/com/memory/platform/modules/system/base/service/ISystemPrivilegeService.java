package com.memory.platform.modules.system.base.service;

import java.util.List;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemPrivilege;


public interface ISystemPrivilegeService extends IBaseService<SystemPrivilege> {
	public <T> List<T> listAccessData(Class<T> accessClass,Class<?> masterClass,String masterValue);

	public void add(Class<?> masterClass, String masterValue,
			Class<?> accessClass, List<String> accessIds);

	public void delete(Class<?> masterClass, String masterValue,
			Class<?> accessClass, List<String> accessIds);

	public <T> List<T>  listAccessData(Class<T> accessClass, Class<?> masterClass,
			String[] masterValues,String accessClassOrderFilde);
}
