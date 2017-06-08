package com.memory.platform.modules.system.base.service;

import java.util.List;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemBasedataLink;


public interface ISystemBasedataLinkService extends IBaseService<SystemBasedataLink> {
	public <T> List<T> listLinkData(Class<T> linkData,Class baseData,String baseDataId);

	public void add(Class<?> basedataType,String basedataId,Class<?> linkdataType,List<String> linkdataIds);
	public void delete(Class<?> basedataType,String basedataId,Class<?> linkdataType,List<String> linkdataIds);

}
