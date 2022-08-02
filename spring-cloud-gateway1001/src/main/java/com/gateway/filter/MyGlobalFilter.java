package com.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 自定义的全局网关过滤器
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("---------------进入自定义的全局过滤器开始......" + new Date());
        // 获取请求中的第一个参数
        String userName = exchange.getRequest().getQueryParams().getFirst("userName");
        if (userName == null) {
            log.info("---------------用户名为null，非法用户---------------");
            // 400，非法的参数
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            // 放行操作
            return exchange.getResponse().setComplete();
        }


        // 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
