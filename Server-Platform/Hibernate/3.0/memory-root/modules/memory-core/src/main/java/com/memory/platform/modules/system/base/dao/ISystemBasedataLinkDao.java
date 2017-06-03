package com.memory.platform.modules.system.base.dao;

import java.util.List;

import com.memory.platform.hibernate4.dao.IBaseDao;
import com.memory.platform.modules.system.base.model.SystemBasedataLink;

/**
 * @author 
 * 
 * @param <T>
 *            模型
 */
public interface ISystemBasedataLinkDao extends IBaseDao<SystemBasedataLink> {
	
	public <T> List<T> listLinkData(Class<T> baseData,String hql);
}
