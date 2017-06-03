package com.memory.platform.modules.system.session;

import com.memory.platform.modules.system.base.model.SystemUser;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 * 
 * @author 
 * 
 */
public class WebSession implements java.io.Serializable {

	private static final long serialVersionUID = 8223495650633855481L;
	
	private SystemUser user;
	
	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return user.getLoginName();
	}

}
