package com.memory.platform.modules.system.base.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.memory.platform.common.util.BrowserUtils;
import com.memory.platform.common.util.ConvertUtils;
import com.memory.platform.common.util.DataUtils;
import com.memory.platform.core.service.impl.BaseServiceImpl;
import com.memory.platform.core.springmvc.WebContextUtils;
import com.memory.platform.hibernate4.dao.IBaseDao;
import com.memory.platform.modules.system.base.model.SystemLog;
import com.memory.platform.modules.system.base.service.ISystemLogService;

@Service("systemLogServiceImpl")
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog> implements ISystemLogService{

	@Autowired
	@Qualifier("baseDaoImpl")
	private IBaseDao baseDao;

	@Override
	public void addLog(String logcontent, Short loglevel, Short operatetype) {

		HttpServletRequest request = WebContextUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		SystemLog log = new SystemLog();
		log.setLogContent(logcontent);
		log.setLogLevel(loglevel);
		log.setOperateType(operatetype);
		log.setNote(ConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperateTime(DataUtils.gettimestamp());
		log.setSystemUser(WebContextUtils.getSessionUser());
		
		baseDao.save(log);
	}
	
	
}
