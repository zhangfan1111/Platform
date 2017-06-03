package com.memory.platform.web.web.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.web.ajax.ExceptionReturn;
import com.memory.platform.hibernate4.search.Search;
import com.memory.platform.hibernate4.search.SearchResult;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemLogService;


/**
 * 日志处理类
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/system/systemLogController")
public class SystemLogController {
//	private static final Logger logger = Logger.getLogger(SystemLogController.class);
	
	@Autowired
	@Qualifier("systemLogServiceImpl")
	private ISystemLogService systemLogService;

	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{page}",method = {RequestMethod.GET,RequestMethod.POST})
	public String page(@PathVariable String page,HttpServletRequest request,Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);
		
		if(page == null || "".equals(page)) {
			return "system/log/list";
		} else {
			return "system/log/"+page;
		}
	}
	
	@RequestMapping(value="directive",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object dataGrid(@RequestParam(required = false) String directive) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			Properties pro = new Properties();
			InputStream in = SystemLogController.class.getClassLoader().getResourceAsStream("config/others/config.properties");
			pro.load(in);
			//重新写入配置文件
			String filePath = SystemLogController.class.getResource("/config/others/config.properties").toString();  
	        //截掉路径的”file:/“前缀  
	        filePath = filePath.substring(6);
            FileOutputStream file = new FileOutputStream(filePath);
			pro.put("system", directive);
			pro.store(file, "系统配置修改"); //这句话表示重新写入配置文件
			
			map.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "error");
		}
		return map;
	}
	
	/**
	 * 日志列表
	 */
	@RequestMapping(value="dataGrid",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Object dataGrid(HttpServletRequest request,HttpSession session,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		try {
			Search<SystemUser> s = new Search<SystemUser>();
			s.setPage(page);
			s.setRows(rows); 
			s.setHql("From SystemLog s ORDER BY operateTime DESC");
			s.setCountHql("SELECT count(*) From SystemLog");
			
			//分析一下这里为什么查询的是SystemLog但是泛型是SystemUser不会报错
			SearchResult<SystemUser> sResult = systemLogService.searchByHql(s);
			
			Map<String, Object> map = sResult.getEasyUIMap();
//			
//			List<SystemLog> l = (List<SystemLog>)map.get("rows");
//			SystemLog sl = l.get(0);
//			SystemUser su = sl.getSystemUser(); 
//			System.out.println(su.getName());
			return map;
		} catch (Exception e) {
//			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
}
