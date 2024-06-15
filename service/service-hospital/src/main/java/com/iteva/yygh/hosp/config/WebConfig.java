package com.iteva.yygh.hosp.config;

import com.iteva.yygh.hosp.interceptor.ValidateSignKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ValidateSignKeyInterceptor validateSignKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateSignKeyInterceptor).addPathPatterns("/api/hosp/**");
    }
}
