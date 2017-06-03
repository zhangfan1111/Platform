package com.memory.platform.modules.system.base.datalink;

import java.util.HashMap;
import java.util.Map;

import com.memory.platform.modules.system.base.model.SystemDept;
import com.memory.platform.modules.system.base.model.SystemResource;
import com.memory.platform.modules.system.base.model.SystemRole;
import com.memory.platform.modules.system.base.model.SystemUser;

public class PrivilegeHelper {

	public enum DATATYPE {
		SYSTEM_USER,SYSTEM_ROLE,SYSTEM_DEPT,SYSTEM_RESOURCE
	}
	
	private static Map<Class<?>,String> class2DataTypeMap = new HashMap<Class<?>,String>();
	private static Map<String,Class<?>> dataType2ClassMap = new HashMap<String,Class<?>>();
	
	static {
		class2DataTypeMap.put(SystemUser.class, DATATYPE.SYSTEM_USER.name());
		class2DataTypeMap.put(SystemRole.class, DATATYPE.SYSTEM_ROLE.name());
		class2DataTypeMap.put(SystemDept.class, DATATYPE.SYSTEM_DEPT.name());
		class2DataTypeMap.put(SystemResource.class, DATATYPE.SYSTEM_RESOURCE.name());
		
		dataType2ClassMap.put(DATATYPE.SYSTEM_USER.name(),SystemUser.class);
		dataType2ClassMap.put(DATATYPE.SYSTEM_ROLE.name(),SystemRole.class);
		dataType2ClassMap.put(DATATYPE.SYSTEM_DEPT.name(),SystemDept.class);
		dataType2ClassMap.put(DATATYPE.SYSTEM_RESOURCE.name(),SystemResource.class);
	}
	
	public static String getDbTypeFlag(Class<?> model) {
		String flag = class2DataTypeMap.get(model);
		return flag;
	}
	
	public static Class<?> getClassByDbTypeFlag(String dbType) {
		Class<?> c = dataType2ClassMap.get(dbType);
		return c;
	}
	
}
