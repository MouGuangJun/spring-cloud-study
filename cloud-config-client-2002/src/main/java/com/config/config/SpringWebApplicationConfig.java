package com.config.config;

import com.config.advice.NotFoundInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringWebApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private NotFoundInterceptor notFoundInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(notFoundInterceptor)
                .addPathPatterns("/**");// 需要被过滤的路径
        // .excludePathPatterns("");// 添加允许路径
    }
}
