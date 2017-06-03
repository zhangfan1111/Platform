package com.memory.platform.web.session;

import com.memory.platform.modules.system.base.model.SystemUser;
import com.memory.platform.modules.system.session.WebSession;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 * 
 * @author 
 * 
 */
public class SessionInfo extends WebSession {

	private static final long serialVersionUID = 8223495650633855481L;
	
	private SystemUser user;
	
	private int taskCount;
	
	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	@Override
	public String toString() {
		return user.getLoginName();
	}
}
