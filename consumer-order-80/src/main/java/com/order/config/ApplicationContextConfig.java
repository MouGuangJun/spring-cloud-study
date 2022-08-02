package com.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * spring需要用的到配置信息
 */
@Configuration
public class ApplicationContextConfig {

    // restTemplate 进行远程http请求调用
    @Bean
    // @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
