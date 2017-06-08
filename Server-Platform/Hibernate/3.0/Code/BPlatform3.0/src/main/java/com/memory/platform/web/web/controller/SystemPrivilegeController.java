package com.memory.platform.web.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.core.web.ajax.AjaxReturn;
import com.memory.platform.modules.system.base.datalink.PrivilegeHelper;
import com.memory.platform.modules.system.base.model.SystemResource;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemPrivilegeService;
import com.memory.platform.modules.system.base.service.ISystemResourceService;

/**
 * 角色相关
 * 
 * @author 
 * @date 2014-6-5 上午11:45:06
 */
@Controller
@RequestMapping("/system/systemPrivilegeController")
public class SystemPrivilegeController {

	private static final Logger logger = LoggerFactory.getLogger(SystemPrivilegeController.class);
	@Autowired
	@Qualifier("systemPrivilegeServiceImpl")
	private ISystemPrivilegeService systemPrivilegeService;

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
		return page;
	}
	

	/**
	 * 列出资源
	 */
	@RequestMapping(value="/loadSystemResource",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object loadSystemResource(@RequestParam(value="master",required=false)String master,
									 @RequestParam(value="masterValue",required=false)String masterValue,
									 @RequestParam(value="id",required=false)String parentResourceId,
									 HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		List<SystemResource> masterResourceList = new ArrayList<SystemResource>();
		List<SystemResource> resourceList = new ArrayList<SystemResource>();
		
		if(master != null && !"".equals(master) && masterValue != null && !"".equals(masterValue)) {
			Class<?> masterClass = PrivilegeHelper.getClassByDbTypeFlag(master);
			masterResourceList = systemPrivilegeService.listAccessData(SystemResource.class, masterClass, masterValue);
		}
		
		if(parentResourceId != null && !"".equals(parentResourceId)) {
			resourceList = systemResourceService.find("From SystemResource s where s.parentId='"+parentResourceId+"'");
		} else {
			resourceList = systemResourceService.find("From SystemResource s where s.parentId is null");
		}
		
		Map<String,SystemResource> masterResourceMap = new HashMap<String,SystemResource>();
		for(SystemResource r : masterResourceList) {
			masterResourceMap.put(r.getId(), r);
		}
		
		for(SystemResource r : resourceList) {
			if(masterResourceMap.containsKey(r.getId())) {
				r.setIsChecked(true);
			}
		}
		return resourceList;
	}
	
	/**
	 * 授权修改
	 */
	@RequestMapping(value="modifyPrivilege",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@Log(operationType="更新操作",operationName="更新用户权限", logLevel=Globals.Log_Type_UPDATE)
	public Object modifyPrivilege(HttpServletRequest request,Model model,
							@RequestParam(value="master",required=true)String master,
							@RequestParam(value="masterValue",required=true)String masterValue,
							@RequestParam(value="access",required=true)String access,
							@RequestParam(value="addAccessIds",required=false)String[]addAccessIds,
							@RequestParam(value="delAccessIds",required=false)String[]delAccessIds) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		try {
			Class<?> masterClass = PrivilegeHelper.getClassByDbTypeFlag(master);
			Class<?> accessClass = PrivilegeHelper.getClassByDbTypeFlag(access);
			if(addAccessIds != null && addAccessIds.length>0) {
				systemPrivilegeService.add(masterClass,masterValue,accessClass,Arrays.asList(addAccessIds));
			}
			if(delAccessIds != null && delAccessIds.length>0) {
				systemPrivilegeService.delete(masterClass,masterValue,accessClass,Arrays.asList(delAccessIds));
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
		
		return new AjaxReturn(true,"操作成功！");
	}
	
}
