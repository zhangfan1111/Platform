package com.memory.platform.modules.system.base.service;

import java.util.List;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.hibernate4.criteria.HqlFilter;
import com.memory.platform.modules.system.base.model.SystemResource;


public interface ISystemResourceService extends IBaseService<SystemResource> {

	public List<SystemResource> loadTreeGrid(HqlFilter hqlFilter);

	/**
	 * 根据角色id查询角色对应的资源
	 * 注意，多个角色可能分配相同的资源,查询结果已经去重了
	 * @param roleIds
	 * @return
	 */
	public List<SystemResource> listResourcesByRoleIds(String roleIds[]);
	/**
	 * 根据部门id查询部门对应的资源
	 * 注意，多个角色可能分配相同的资源,查询结果已经去重了
	 * @param roleIds
	 * @return
	 */
	public List<SystemResource> listResourcesByDeptIds(String deptIds[]);
	
	/**
	 * 根据用户id查询用户对应的资源
	 * @param roleIds
	 * @return
	 */
	public List<SystemResource> listResourcesByUserId(String userId);
	
	/**
	 * 根据分配给用户的角色查询对应的资源
	 * @param roleIds
	 * @return
	 */
	public List<SystemResource> listResourcesByUserRole(String userId);
	
	/**
	 * 根据分配给用户的部门查询对应的资源
	 * @param roleIds
	 * @return
	 */
	public List<SystemResource> listResourcesByUserDept(String userId);
}
