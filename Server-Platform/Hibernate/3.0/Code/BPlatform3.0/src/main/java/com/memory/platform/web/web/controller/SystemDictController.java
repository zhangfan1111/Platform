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
import com.memory.platform.modules.system.base.model.SystemDict;
import com.memory.platform.modules.system.base.service.ISystemDictService;
import com.memory.platform.modules.system.base.util.E;

/**
 * 部门相关
 * 
 * @author 
 * @date 2014-6-5 上午11:45:06
 */
@Controller
@RequestMapping("/system/systemDictController")
public class SystemDictController {

//	private static final Logger logger = LoggerFactory.getLogger(SystemDictController.class);
	@Autowired
	@Qualifier("systemDictServiceImpl")
	private ISystemDictService systemDictService;

	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{page}",method = {RequestMethod.GET,RequestMethod.POST})
	public String page(@PathVariable String page,HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		if(page == null || "".equals(page)) {
			return "system/dict/list";
		} else {
			return "system/dict/"+page;
		}
	}
	
	
	/**
	 * 部门列表页面
	 */
	@RequestMapping(value="treeGrid",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object treeGrid(@RequestParam(value="id",required=false)String parentId) {
		List<SystemDict> list = null;
		if(parentId != null && !"".equals(parentId)) {
			list = systemDictService.find("From SystemDict s where s.parentId='"+parentId+"'");
		} else {
			list = systemDictService.find("From SystemDict s");
		}
		return list;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="更新操作",operationName="用户更新字典信息", logLevel=Globals.Log_Type_UPDATE)
	public Object save(@ModelAttribute("systemDict") SystemDict systemDict) {
		if(systemDict.getType() == null || "".equals(systemDict.getType())) {
			systemDict.setType(E.DictType.COMMENT.name());
		}
		systemDictService.saveOrUpdate(systemDict);
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="删除操作",operationName="用户删除字典信息", logLevel=Globals.Log_Type_DEL)
	public Object delete(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemDict s = systemDictService.getById(id);
			systemDictService.delete(s);
		}
		return new AjaxReturn(true,"操作成功！");
	}
	
	@RequestMapping(value="/getById/{id}")
	@ResponseBody
	@Log(operationType="查询操作",operationName="用户查看字典信息", logLevel=Globals.Log_Type_OTHER)
	public Object getById(@PathVariable String id) {
		if(id != null && !"".equals(id)) {
			SystemDict s = systemDictService.getById(id);
			return s;
		}
		return null;
	}
	@RequestMapping(value="/getByCode/{code}")
	@ResponseBody
	public Object getByCode(@PathVariable String code){
		if(code != null && !"".equals(code)) {
			List<SystemDict> systemDicts = systemDictService.find("FROM SystemDict sd WHERE sd.parentId = (SELECT s.id FROM SystemDict s WHERE s.code = '"+code+"')");
			return systemDicts;
		}
		return null;
	}
}
