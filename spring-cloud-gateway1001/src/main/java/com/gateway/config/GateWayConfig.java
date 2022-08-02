package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator newsGuoNei(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        // 其中[*]表示该属性为数组
        // 第一个参数 = spring.cloud.gateway.routes[id]
        // path = spring.cloud.gateway.routes[predicates][Path]
        // uri = spring.cloud.gateway.routes[uri]
        // 此时访问：http://localhost:1001/guonei，会自动跳转到http://news.baidu.com/guonei
        // 相当于使用了Spring Cloud GateWay进行了一次转发
        return routes.route("path_route_guoji", builder -> builder.path("/guonei")
                .uri("http://news.baidu.com/guonei")).build();
    }


    // 需要配置多个路由的情况
    @Bean
    public RouteLocator newsGuoJi(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        // 此时访问：http://localhost:1001/guoji，会自动跳转到http://news.baidu.com/guoji
        return routes.route("path_route_guoji", builder -> builder.path("/guoji")
                .uri("http://news.baidu.com/guoji")).build();
    }
}
