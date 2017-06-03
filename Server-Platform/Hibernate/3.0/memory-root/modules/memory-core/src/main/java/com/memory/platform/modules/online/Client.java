package com.memory.platform.modules.online;

import java.util.List;
import java.util.Map;

import com.memory.platform.modules.system.base.model.SystemResource;
import com.memory.platform.modules.system.base.model.SystemUser;

/**
 * 在线用户对象
 * 管理在线用户，及用户的资源
 * 这部分取名为clien是为了扩展，以后在线的不只是系统用户，还要管理在线的客户端，第三方系统等。将在线的使用都及其相关资源数据都放到这里，方便做缓存，目前就对resources做了缓存
 * 
 * @date 2014-10-23
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private SystemUser user;

	private Map<String, List<SystemResource>> resources;
	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	/**
	 *登录时间
	 */
	private java.util.Date loginDateTime;

	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}


	public Map<String, List<SystemResource>> getFunctions() {
		return resources;
	}

	public void setFunctions(Map<String, List<SystemResource>> resources) {
		this.resources = resources;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return loginDateTime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.loginDateTime = logindatetime;
	}


}
