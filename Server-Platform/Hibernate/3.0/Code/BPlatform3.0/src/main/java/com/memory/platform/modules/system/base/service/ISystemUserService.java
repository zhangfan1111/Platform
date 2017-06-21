package com.memory.platform.modules.system.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.utils.file.model.SysUser;


public interface ISystemUserService extends IBaseService<SystemUser> {


	/**
	 * 查询用户的所有部门
	 * @param userId
	 * @return
	 */
	public List<SystemDept> listUserDepts(String userId);
	/**
	 * 查询用户的所有角色
	 * @param userId
	 * @return
	 */
	public List<SystemRole> listUserRoles(String userId);
	
	public List<SystemUser> findUsers(String loginName, String pwd);
	
	public void saveClientInfo(SystemUser user, HttpServletRequest request, String sessionId);
	
	/**
	 * 批量保存用户
	 * @param list
	 */
	void saveUsers(List<SysUser> list);
}
