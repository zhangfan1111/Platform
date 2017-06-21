package com.memory.platform.web.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.common.util.MD5andKL;
import com.memory.platform.common.util.StaticMethod;
import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.core.constants.WebConstants;
import com.memory.platform.core.springmvc.DateConvertEditor;
import com.memory.platform.core.web.ajax.AjaxReturn;
import com.memory.platform.core.web.ajax.ExceptionReturn;
import com.memory.platform.hibernate4.search.Search;
import com.memory.platform.hibernate4.search.SearchResult;
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemDeptService;
import com.memory.platform.modules.system.base.service.ISystemRoleService;
import com.memory.platform.modules.system.base.service.ISystemUserService;
import com.memory.platform.web.session.SessionInfo;
import com.utils.ResultData;
import com.utils.file.ExcelUtil;
import com.utils.file.model.SysUser;

/**
 * 用户相关
 * 
 * @author
 * @date 2014-6-5 上午11:45:06
 */
@Controller
@RequestMapping("/system/systemUserController")
public class SystemUserController {

	private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);
	@Autowired
	@Qualifier("systemUserServiceImpl")
	private ISystemUserService systemUserService;

	@Autowired
	@Qualifier("systemRoleServiceImpl")
	private ISystemRoleService systemRoleService;

	@Autowired
	@Qualifier("systemDeptServiceImpl")
	private ISystemDeptService systemDeptService;

	@Autowired
	@Qualifier("systemBasedataLinkServiceImpl")
	private ISystemBasedataLinkService systemBasedataLinkService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 1. 使用spring自带的CustomDateEditor
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, true));
		// 2. 自定义的PropertyEditorSupport
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/{page}", method = { RequestMethod.GET, RequestMethod.POST })
	public String page(@PathVariable String page, HttpServletRequest request, Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);

		if (page == null || "".equals(page)) {
			return "system/user/list";
		} else {
			return "system/user/" + page;
		}
	}

	@RequestMapping(value = "/getById/{id}")
	@ResponseBody
	@Log(operationType = "查询操作", operationName = "查询用户详细信息", logLevel = Globals.Log_Type_OTHER)
	public Object getById(@PathVariable String id) {
		try {
			if (id != null && !"".equals(id)) {
				SystemUser s = systemUserService.getById(id);
				// System.out.println(MD5andKL.KL(s.getPwd()));
				// System.out.println(MD5andKL.JM(s.getPwd()));
				// s.setPwd(MD5andKL.KL(s.getPwd()));
				return s;
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		return null;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType = "更新操作", operationName = "更新用户信息", logLevel = Globals.Log_Type_UPDATE)
	public Object save(@ModelAttribute("systemUser") SystemUser systemUser) {
		try {
			if (systemUser.getId() != null) {
				SystemUser s = systemUserService.getById(systemUser.getId());
				if (systemUser.getPwd().equals(s.getPwd())) {
					systemUser.setPwd(s.getPwd());
				} else {
					systemUser.setPwd(MD5andKL.MD5(systemUser.getPwd()));
				}
			} else {
				systemUser.setPwd(MD5andKL.MD5(systemUser.getPwd()));
			}
			systemUserService.saveOrUpdate(systemUser);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		return true;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType = "删除操作", operationName = "删除用户", logLevel = Globals.Log_Type_DEL)
	public Object delete(HttpSession session, Locale locale, @RequestParam(required = false) String id) {
		try {
			SystemUser user = systemUserService.getById(id);
			systemUserService.delete(user);
			return true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType = "更新操作", operationName = "修改用户密码", logLevel = Globals.Log_Type_UPDATE)
	public Object updatePwd(HttpSession session, @RequestParam("pwd") String pwd) {
		try {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(WebConstants.CURRENT_USER);
			SystemUser user = sessionInfo.getUser();
			user.setPwd(MD5andKL.MD5(pwd));
			systemUserService.update(user);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		return true;
	}

	/**
	 * 用户列表页面
	 */
	@RequestMapping(value = "dataGrid", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@Log(operationType = "查询操作", operationName = "查看用户列表", logLevel = Globals.Log_Type_OTHER)
	public Object dataGrid(HttpServletRequest request, HttpSession session,
			@RequestParam(required = false) String search, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		search = StaticMethod.null2String(search);
		try {
			Search<SystemUser> s = new Search<SystemUser>();
			s.setPage(page);
			s.setRows(rows);
			s.setHql("From SystemUser s WHERE s.loginName LIKE '%" + search + "%'");
			s.setCountHql("SELECT count(*) From SystemUser s WHERE s.loginName LIKE '%" + search + "%'");

			SearchResult<SystemUser> sResult = systemUserService.searchByHql(s);
			Map<String, Object> map = sResult.getEasyUIMap();
			return map;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 用户分配的角色树
	 */
	@RequestMapping(value = "loadUserRoleTree/", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object loadUserRoleTree(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "id", required = false) String parentRoleId, HttpServletRequest request, Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);

		List<SystemRole> userRoleList = null;
		List<SystemRole> roleList = null;

		userRoleList = systemBasedataLinkService.listLinkData(SystemRole.class, SystemUser.class, userId);

		if (parentRoleId != null && !"".equals(parentRoleId)) {
			roleList = systemRoleService.find("From SystemRole s where s.parentId='" + parentRoleId + "'");
		} else {
			roleList = systemRoleService.find("From SystemRole s where s.parentId is null");
		}

		Map<String, SystemRole> userRoleMap = new HashMap<String, SystemRole>();
		for (SystemRole r : userRoleList) {
			userRoleMap.put(r.getId(), r);
		}

		for (SystemRole r : roleList) {
			if (userRoleMap.containsKey(r.getId())) {
				r.setIsChecked(true);
			}
		}
		return roleList;
	}

	/**
	 * 用户角色授权
	 */
	@RequestMapping(value = "modifyRole", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@Log(operationType = "更新操作", operationName = "用户角色授权", logLevel = Globals.Log_Type_UPDATE)
	public Object modifyRole(HttpServletRequest request, Model model,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "addRoleIds", required = false) String[] addRoleIds,
			@RequestParam(value = "delRoleIds", required = false) String[] delRoleIds) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);

		try {
			if (addRoleIds != null && addRoleIds.length > 0) {
				systemBasedataLinkService.add(SystemUser.class, userId, SystemRole.class, Arrays.asList(addRoleIds));
			}
			if (delRoleIds != null && delRoleIds.length > 0) {
				systemBasedataLinkService.delete(SystemUser.class, userId, SystemRole.class, Arrays.asList(delRoleIds));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return new AjaxReturn(true, "操作成功！");
	}

	/**
	 * 用户分配的部门树
	 */
	@RequestMapping(value = "loadUserDeptTree/", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object loadUserDeptTree(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "id", required = false) String parentDeptId, HttpServletRequest request, Model model) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);

		List<SystemDept> userDeptList = null;
		List<SystemDept> deptList = null;

		userDeptList = systemBasedataLinkService.listLinkData(SystemDept.class, SystemUser.class, userId);

		if (parentDeptId != null && !"".equals(parentDeptId)) {
			deptList = systemDeptService.find("From SystemDept s where s.parentId='" + parentDeptId + "'");
		} else {
			deptList = systemDeptService.find("From SystemDept s where s.parentId is null");
		}

		Map<String, SystemDept> userDeptMap = new HashMap<String, SystemDept>();
		for (SystemDept r : userDeptList) {
			userDeptMap.put(r.getId(), r);
		}

		for (SystemDept r : deptList) {
			if (userDeptMap.containsKey(r.getId())) {
				r.setIsChecked(true);
			}
		}
		return deptList;
	}

	/**
	 * 用户部门授权
	 */
	@RequestMapping(value = "modifyDept", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@Log(operationType = "更新操作", operationName = "用户部门授权", logLevel = Globals.Log_Type_UPDATE)
	public Object modifyDept(HttpServletRequest request, Model model,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "addDeptIds", required = false) String[] addDeptIds,
			@RequestParam(value = "delDeptIds", required = false) String[] delDeptIds) {
		Map requestMap = request.getParameterMap();
		model.addAllAttributes(requestMap);

		System.out.println(userId);
		System.out.println(Arrays.toString(addDeptIds));
		System.out.println(Arrays.toString(delDeptIds));

		try {
			if (addDeptIds != null && addDeptIds.length > 0) {
				systemBasedataLinkService.add(SystemUser.class, userId, SystemDept.class, Arrays.asList(addDeptIds));
			}
			if (delDeptIds != null && delDeptIds.length > 0) {
				systemBasedataLinkService.delete(SystemUser.class, userId, SystemDept.class, Arrays.asList(delDeptIds));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return new AjaxReturn(true, "操作成功！");
	}

	/**
	 * 批量导入用户
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	@Log(operationType = "批量更新操作", operationName = "批量增加/更新用户信息", logLevel = Globals.Log_Type_UPDATE)
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		long filesize = file.getSize();
		if (filesize / 1024 / 1024 > 50) {
			return ResultData.error("文件不能大于50M,上传失败");
		} else if (filesize / 1024 < 3) {
			return ResultData.error("文件不能小于3k,上传失败");
		}
		InputStream inputStream = file.getInputStream();
		String fileName = file.getOriginalFilename();
		List<SysUser> list = new ExcelUtil().importDataFromExcel(SysUser.class, inputStream, fileName);
		systemUserService.saveUsers(list);
		return ResultData.success("导入成功！");
	}
}
