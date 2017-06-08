package com.memory.platform.web.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.core.web.ajax.AjaxReturn;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemResourceService;
import com.memory.platform.modules.system.base.service.ISystemRoleService;

/**
 * 角色相关
 * 
 * @author 
 * @date 2014-6-5 上午11:45:06
 */
@Controller
@RequestMapping("/system/systemRoleController")
public class SystemRoleController {

	private static final Logger logger = LoggerFactory.getLogger(SystemRoleController.class);
	@Autowired
	@Qualifier("systemRoleServiceImpl")
	private ISystemRoleService systemRoleService;

	@Autowired
	@Qualifier("systemBasedataLinkServiceImpl")
	private ISystemBasedataLinkService systemBasedataLinkService;
	
	@Autowired
	@Qualifier("systemResourceServiceImpl")
	private ISystemResourceService systemResourceService;

	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{page}",method = {RequestMethod.GET,RequestMethod.POST})
	public String page(@PathVariable String page,HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		if(page == null || "".equals(page)) {
			return "system/role/list";
		} else {
			return "system/role/"+page;
		}
	}
	
	
	/**
	 * 角色列表页面
	 */
	@RequestMapping(value="treeGrid",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@Log(operationType="查询操作",operationName="查看角色列表", logLevel=Globals.Log_Type_OTHER)
	public Object treeGrid(@RequestParam(value="id",required=false)String parentId) {
		List<SystemRole> list = null;
		if(parentId != null && !"".equals(parentId)) {
			list = systemRoleService.find("From SystemRole s where s.parentId='"+parentId+"'");
		} else {
			list = systemRoleService.find("From SystemRole s");
		}
		return list;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="更新操作",operationName="更新角色信息", logLevel=Globals.Log_Type_UPDATE)
	public Object save(@ModelAttribute("systemRole") SystemRole systemRole) {
		systemRoleService.saveOrUpdate(systemRole);
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="删除操作",operationName="删除角色", logLevel=Globals.Log_Type_DEL)
	public Object delete(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemRole s = systemRoleService.getById(id);
			systemRoleService.delete(s);
		}
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/getById/{id}")
	@ResponseBody
	@Log(operationType="查询操作",operationName="查看角色详细信息", logLevel=Globals.Log_Type_OTHER)
	public Object getById(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemRole s = systemRoleService.getById(id);
			return s;
		}
		return null;
	}
}
