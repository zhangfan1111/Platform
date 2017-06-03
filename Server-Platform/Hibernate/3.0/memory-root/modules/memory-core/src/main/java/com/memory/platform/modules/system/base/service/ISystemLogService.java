package com.memory.platform.modules.system.base.service;

import com.memory.platform.core.service.IBaseService;
import com.memory.platform.modules.system.base.model.SystemLog;

public interface ISystemLogService extends IBaseService<SystemLog>{
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 */
	public void addLog(String LogContent, Short loglevel,Short operatetype);
}
