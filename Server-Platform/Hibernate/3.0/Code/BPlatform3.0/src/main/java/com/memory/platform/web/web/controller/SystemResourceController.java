package com.memory.platform.web.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.memory.platform.core.constants.WebConstants;
import com.memory.platform.modules.system.base.model.SystemResource;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemResourceService;
import com.memory.platform.web.session.SessionInfo;

/**
 * 后台资源、系统菜单相关
 * 
 * @author 
 * @date 2014-6-17 17:31:43
 */
@Controller
@RequestMapping("/system/systemResourceController")
public class SystemResourceController {

//	private static final Logger logger = LoggerFactory.getLogger(SystemResourceController.class);
	@Autowired
	@Qualifier("systemResourceServiceImpl")
	private ISystemResourceService systemResourceService;

	@Autowired
	@Qualifier("systemBasedataLinkServiceImpl")
	private ISystemBasedataLinkService systemBasedataLinkService;
	
	/**
	 * index
	 */
	@RequestMapping(value="/{page}",method = {RequestMethod.GET,RequestMethod.POST})
	public String page(@PathVariable String page,HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		if(page == null || "".equals(page)) {
			return "system/resource/systemResource";
		} else {
			return "system/resource/"+page;
		}
	}

	/**
	 * 获得资源treeGrid
	 */
	@RequestMapping(value="treeGrid",method = RequestMethod.POST)
	@ResponseBody
	public Object treeGrid(HttpServletRequest request,HttpSession session,@RequestParam(value="id",required=false)String parentId) {
		List<SystemResource> list = null;
		if(parentId == null || "".equals(parentId)) {
			list = systemResourceService.find("From SystemResource s where s.parentId is null order by s.orderCode asc ");
		} else {
			list = systemResourceService.find("From SystemResource s where s.parentId='"+parentId+"'  order by s.orderCode asc");
		}
		return list;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="更新操作",operationName="更新资源信息", logLevel=Globals.Log_Type_UPDATE)
	public Object save(@ModelAttribute("systemResource") SystemResource systemResource) {
		systemResourceService.saveOrUpdate(systemResource);
		return true;
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="删除操作",operationName="删除资源", logLevel=Globals.Log_Type_DEL)
	public Object delete(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemResource s = systemResourceService.getById(id);
			systemResourceService.delete(s);
		}
		return true;
	}
	
	@RequestMapping(value="/getById/{id}")
	@ResponseBody
	@Log(operationType="查询操作",operationName="查询资源详细信息", logLevel=Globals.Log_Type_OTHER)
	public Object getById(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemResource s = systemResourceService.getById(id);
			return s;
		}
		return null;
	}
	
	@RequestMapping(value="/getMainResource",method = RequestMethod.POST)
	@ResponseBody
	public Object getMainResource(HttpSession session) {
		
		SessionInfo sessinoInfo =  (SessionInfo)session.getAttribute(WebConstants.CURRENT_USER);
		SystemUser currentUser = sessinoInfo.getUser();
		String userId = currentUser.getId();
		
		//查询角色对应的资源，注意，多个角色可能分配相同的资源,查询结果已经去重了
		List<SystemResource> resourceList = new ArrayList<SystemResource>();
		resourceList = systemResourceService.listResourcesByUserRole(userId);
		
		//查询用户授权的资源，用户所有相关授权模型查询出的授权数据,查询结果已经去重了
//		resourceList = systemResourceService.listResourcesByUserId(userId);
		
		return resourceList;
	}
	
	@RequestMapping(value="/getAllResource",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="查询操作",operationName="获取资源列表", logLevel=Globals.Log_Type_OTHER)
	public Object getAllResource(HttpSession session) {
		List<SystemResource> list = null;
		list = systemResourceService.find("From SystemResource s where s.type='1' or s.type='2' order by s.orderCode asc");
		return list;
	}
	
	/**
	 * 查找系统的所有菜单
	 * 
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String all(PrintWriter writer) throws IOException {
		return "system/resource/systemResource";
	}

}
