package com.ribbon.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 修改Ribbon的负载均衡策略
 */
@Configuration
public class MySelfRule {

    /**
     * 随机的方式
     */
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
