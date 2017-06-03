package com.memory.platform.test.core.annotation;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.memory.platform.core.interceptors.LogInterceptor;
import com.memory.platform.core.springmvc.SpringContextHolder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring.xml")
//public class LogAnnotationTest {
//
//	@Test
//	public void test1() {
//		Map<String,LogInterceptor> ll = (Map<String,LogInterceptor>)SpringContextHolder.getBean(LogInterceptor.class);
//		System.out.println(ll);
//	}
//}
