package com.memory.platform.web.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.core.constants.WebConstants;
import com.memory.platform.core.service.IBaseService;
import com.memory.platform.core.web.ajax.AjaxReturn;
import com.memory.platform.core.web.ajax.ExceptionReturn;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemLogService;
import com.memory.platform.modules.system.base.service.ISystemUserService;
import com.memory.platform.web.session.SessionInfo;

/**
 * 用户登录相关
 * 
 * @author
 * @date 2014-6-5 上午11:45:00
 */
@Controller
@RequestMapping("/system/loginController")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	@Qualifier("baseServiceImpl")
	private IBaseService baseService;

	@Autowired
	@Qualifier("systemLogServiceImpl")
	private ISystemLogService systemLogService;

	@Autowired
	@Qualifier("systemUserServiceImpl")
	private ISystemUserService systemUserService;

	/** 限制时间 */
	/* @Value("${limit.millis:3600000}") */
	private Long millis;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 退出登录
	 */
	@RequestMapping("/logout")
	@ResponseBody
	@Log(operationType="退出操作",operationName="用户退出系统", logLevel=Globals.Log_Type_EXIT)
	public Object logout(HttpSession session, Locale locale) {
		try {
			session.removeAttribute(WebConstants.CURRENT_USER);
			session.invalidate();
			return new AjaxReturn(true, "退出系统成功！");
			// return new AjaxReturn(true,
			// messageSource.getMessage("common.login", null, locale));
			// return new AjaxReturn(true,
			// messageSource.getMessage("common.logout", new
			// Object[]{"超级管理员","成功！"}, locale));
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	@Log(operationType="登录操作",operationName="用户登录系统", logLevel=Globals.Log_Type_LOGIN)
	public Object login(@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "password", required = true) String password, HttpSession session,
			HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(loginName)) {
				return new AjaxReturn(false, "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new AjaxReturn(false, "密码不能为空！");
			}

			String md5Pwd = DigestUtils.md5Hex(password);

			List<SystemUser> userList = systemUserService.findUsers(loginName, md5Pwd);

			if (userList != null && userList.size() > 0) {
				SystemUser user = userList.get(0);

				systemUserService.saveClientInfo(user, request, session.getId());

//				HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//				Map<String, Object> m = BeanToMapUtil.convertBean(user);
//				hashOps.putAll("userinfo" + user.getId(), m);
//				logger.info("用户信息：", hashOps.entries("userinfo").toString());
//				Map<String, Object> userMap = hashOps.entries("userinfo" + user.getId());
//				SystemUser u = new SystemUser();
//				BeanToMapUtil.convertMap(userMap, u);

				SessionInfo sessinoInfo = new SessionInfo();
				sessinoInfo.setUser(user);
				session.setAttribute(WebConstants.CURRENT_USER, sessinoInfo);
				logger.info("[{}]登陆成功", sessinoInfo.getUser().getName());
				return new AjaxReturn(true, "登陆成功");
			}

			return new AjaxReturn(false, "用户名或密码错误！");

		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 转到找回用户密码页面
	 */
	@RequestMapping(value = "/findpwd", method = RequestMethod.GET)
	public String findpwd() {
		return "user/findpwd";
	}
}
