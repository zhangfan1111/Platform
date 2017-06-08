package com.memory.platform.modules.system.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.modules.system.base.dao.ISystemBasedataLinkDao;
import com.memory.platform.modules.system.base.datalink.BasedataLinkHelper;
import com.memory.platform.modules.system.base.model.SystemBasedataLink;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;

@Service("systemBasedataLinkServiceImpl")
public class SystemBasedataLinkServiceImpl extends BaseServiceImpl<SystemBasedataLink> implements ISystemBasedataLinkService {

	@Autowired
	@Qualifier("systemBasedataLinkDaoImpl")
	private ISystemBasedataLinkDao systemBasedataLinkDao;
	


	@Override
	public <T> List<T> listLinkData(Class<T> linkData,Class baseData,String baseDataId) {
		List<T> list;
		
		String linkDataTable = linkData.getName();
		
		String linkDataDbFlag = BasedataLinkHelper.getDbTypeFlag(linkData);
		String baseDataDbFlag = BasedataLinkHelper.getDbTypeFlag(baseData);
		String hql = "select b From "+linkDataTable+" b, com.memory.platform.modules.system.base.model.SystemBasedataLink l where l.basedataType='"+baseDataDbFlag+"' and l.basedataValue='"+baseDataId+"' and l.linkdataType='"+linkDataDbFlag+"' and l.linkdataValue=b.id";
		
		if(baseDataId.equals("whcd")) {
			hql = "select b From "+linkDataTable+" b, com.memory.platform.modules.system.base.model.SystemBasedataLink l where l.basedataType='"+baseDataDbFlag + "' and l.linkdataType='"+linkDataDbFlag+"' and l.linkdataValue=b.id";
		}
		
		list = systemBasedataLinkDao.listLinkData(linkData,hql);
		return list;
	}

	@Override
	public void add(Class<?> basedataType, String basedataId,
			Class<?> linkdataType, List<String> linkdataIds) {
		String basedataTypeFlag = BasedataLinkHelper.getDbTypeFlag(basedataType);
		String linkdataTypeFlag = BasedataLinkHelper.getDbTypeFlag(linkdataType);
		
		for(String linkdataId : linkdataIds) {
			SystemBasedataLink sbl = new SystemBasedataLink();
			sbl.setBasedataType(basedataTypeFlag);
			sbl.setBasedataValue(basedataId);
			sbl.setLinkdataType(linkdataTypeFlag);
			sbl.setLinkdataValue(linkdataId);
			this.save(sbl);
		}
	}

	@Override
	public void delete(Class<?> basedataType, String basedataId,
			Class<?> linkdataType, List<String> linkdataIds) {
		String basedataTypeFlag = BasedataLinkHelper.getDbTypeFlag(basedataType);
		String linkdataTypeFlag = BasedataLinkHelper.getDbTypeFlag(linkdataType);
		
		String hql = "delete from SystemBasedataLink l where l.basedataType=:basedataType and l.basedataValue=:basedataValue and l.linkdataType=:linkdataType and l.linkdataValue=:linkdataValue";
		Map<String,Object> params = new HashMap<String,Object>();
		for(String linkdataId : linkdataIds) {
			params.put("basedataType", basedataTypeFlag);
			params.put("basedataValue", basedataId);
			params.put("linkdataType", linkdataTypeFlag);
			params.put("linkdataValue", linkdataId);
			
			systemBasedataLinkDao.executeHql(hql,params);
			
			params.clear();
		}
	}
	
}
