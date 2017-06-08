package com.memory.platform.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.memory.platform.core.constants.Globals;

/**
 * <ul>
 * <li>要执行的操作类型比如：add操作 operationType() default ""</li> 
 * <li>要执行的具体操作比如：添加用户 operationName() default ""</li> 
 * </ul>
 * 
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年6月7日
 * @modify by reason:{方法名}:{原因}
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/** 要执行的操作类型比如：add操作 **/
	String operationType() default "";

	/** 要执行的具体操作比如：添加用户 **/
	String operationName() default "";
	
	/** 日志级别，参照Globals **/
	int logLevel() default Globals.Log_Type_OTHER;
	
	
}
