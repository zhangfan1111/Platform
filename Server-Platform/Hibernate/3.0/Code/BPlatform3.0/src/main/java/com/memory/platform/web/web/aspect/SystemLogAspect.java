package com.memory.platform.web.web.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memory.platform.core.annotation.Log;
import com.memory.platform.core.constants.Globals;
import com.memory.platform.modules.system.base.service.ISystemLogService;

@Aspect
public class SystemLogAspect {
	@Resource
	private ISystemLogService systemLogService;

	private static Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	/**
	 * 只针对带有@Log的方法
	 */
	@Pointcut("@annotation(com.memory.platform.core.annotation.Log)")
	public void logAspect() {
	}

	/**
	 * 只对Controller进行pointcut
	 */
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerAspect() {

	}

	@After("logAspect()")
	public Object traceAround(JoinPoint pjp) throws Throwable {
		return doAroundController(pjp);
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 * @throws ClassNotFoundException 
	 * @throws NotFoundException 
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws ClassNotFoundException, NotFoundException {
		Object[] args = joinPoint.getArgs();
		String classType = joinPoint.getTarget().getClass().getName();
		Class<?> clazz = Class.forName(classType);
		String clazzName = clazz.getName();
		String methodName = joinPoint.getSignature().getName(); // 获取方法名称
		// 获取参数名称和值
		Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);
		logger.info(classType + " : " + methodName);
		logger.info("Args : " + nameAndArgs .toString());
	}

	/**
	 * 获取参数名称和值
	 * @param cls
	 * @param clazzName
	 * @param methodName
	 * @param args
	 * @return
	 * @throws NotFoundException
	 */
	private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException { 
		Map<String,Object > map=new HashMap<String,Object>();
		
        ClassPool pool = ClassPool.getDefault();  
        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
        ClassClassPath classPath = new ClassClassPath(cls);  
        pool.insertClassPath(classPath);  
          
        CtClass cc = pool.get(clazzName);  
        CtMethod cm = cc.getDeclaredMethod(methodName);  
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null) {  
            // exception  
        }  
       // String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < cm.getParameterTypes().length; i++){  
            map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名  
        }  
        
        //Map<>
        return map;  
    }  
	
	private Object doAroundController(JoinPoint joinPoint) throws Throwable {
		String operationType = "";
		String operationName = "";
		int logLevel = Globals.Log_Type_OTHER;
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						operationType = method.getAnnotation(Log.class).operationType();
						operationName = method.getAnnotation(Log.class).operationName();
						logLevel = method.getAnnotation(Log.class).logLevel();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("异常信息:{}", e.getMessage());
			e.printStackTrace();
		}

		return doAround(operationType, operationName, logLevel, joinPoint);
	}

	protected Object doAround(String operationType, String operationName, int logLevel, JoinPoint pjp) throws Throwable {
		// 日志记录
		String message = "操作：" + operationName;
		message += "；操作类型：" + (pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + "()") + "."
				+ operationType;
		systemLogService.addLog(message, logLevel, Globals.Log_Leavel_INFO);
		return null;
	}
}
