package com.fri.interceptor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//核录桩请求统一校验deviceNO 为空情况
public class MyInterceptorAdapter implements WebMvcConfigurer{
	

	@Bean
    public MyInterceptor idempotentInterceptor(){
        return new MyInterceptor();
    }
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截(addPathPatterns)和排除拦截(excludePathPatterns)
    	registry.addInterceptor(idempotentInterceptor()).addPathPatterns("/check/**");
    }
}