package com.memory.platform.web.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.service.ISystemDeptService;

/**
 * 部门相关
 * 
 * @author 
 * @date 2014-6-5 上午11:45:06
 */
@Controller
@RequestMapping("/system/systemDeptController")
public class SystemDeptController {

//	private static final Logger logger = LoggerFactory.getLogger(SystemDeptController.class);
	@Autowired
	@Qualifier("systemDeptServiceImpl")
	private ISystemDeptService systemDeptService;

	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{page}",method = {RequestMethod.GET,RequestMethod.POST})
	public String page(@PathVariable String page,HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		if(page == null || "".equals(page)) {
			return "system/dept/list";
		} else {
			return "system/dept/"+page;
		}
	}
	
	
	/**
	 * 部门列表页面
	 */
	@RequestMapping(value="treeGrid",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@Log(operationType="查看操作",operationName="用户查看部门列表", logLevel=Globals.Log_Type_OTHER)
	public Object treeGrid(@RequestParam(value="id",required=false)String parentId) {
		List<SystemDept> list = null;
		if(parentId != null && !"".equals(parentId)) {
			list = systemDeptService.find("From SystemDept s where s.parentId='"+parentId+"'");
		} else {
			list = systemDeptService.find("From SystemDept s");
		}
		return list;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="更新操作",operationName="用户更新用户部门信息", logLevel=Globals.Log_Type_UPDATE)
	public Object save(@ModelAttribute("systemDept") SystemDept systemDept) {
		systemDeptService.saveOrUpdate(systemDept);
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="删除操作",operationName="用户删除部门", logLevel=Globals.Log_Type_DEL)
	public Object delete(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemDept s = systemDeptService.getById(id);
			systemDeptService.delete(s);
		}
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/getById/{id}")
	@ResponseBody
	@Log(operationType="查询操作",operationName="用户查看部门详情", logLevel=Globals.Log_Type_OTHER)
	public Object getById(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemDept s = systemDeptService.getById(id);
			return s;
		}
		return null;
	}
	
}
