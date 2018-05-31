package com.k2data.k2app.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 *
 * @author lidong9144@163.com 17-3-21.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//    private final LoggerInterceptor loggerInterceptor;
//
//    @Autowired
//    public WebMvcConfig(LoggerInterceptor loggerInterceptor) {
//        this.loggerInterceptor = loggerInterceptor;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new LoggerInterceptor());
    }
    
}
