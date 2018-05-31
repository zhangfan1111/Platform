package com.k2data.k2app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k2data.k2app.service.SchedualServiceHi;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${kmx.port1}")
	String port;
    
    @Autowired
    SchedualServiceHi schedualServiceHi;
    
    @GetMapping(value = "/hi")
	public String home(@RequestParam String name) {
		return "hi "+name+",i am from port:" +port;
	}
    
    @GetMapping(value = "/sayhi")
    public String sayHi(@RequestParam String name){
        return schedualServiceHi.sayHiFromClientOne(name);
    }
}