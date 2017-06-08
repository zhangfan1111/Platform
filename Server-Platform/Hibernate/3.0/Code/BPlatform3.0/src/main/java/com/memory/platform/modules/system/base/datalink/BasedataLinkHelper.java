package com.memory.platform.modules.system.base.datalink;

import java.util.HashMap;
import java.util.Map;

import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;

public class BasedataLinkHelper {

	public enum DATATYPE {
		SYSTEM_USER,SYSTEM_ROLE,SYSTEM_DEPT
	}
	
	private static Map<Class<?>,String> dataTypeMap = new HashMap<Class<?>,String>();
	
	static {
		dataTypeMap.put(SystemUser.class, DATATYPE.SYSTEM_USER.name());
		dataTypeMap.put(SystemRole.class, DATATYPE.SYSTEM_ROLE.name());
		dataTypeMap.put(SystemDept.class, DATATYPE.SYSTEM_DEPT.name());
	}
	
	public static String getDbTypeFlag(Class<?> model) {
		String flag = dataTypeMap.get(model);
		return flag;
	}
	
}
