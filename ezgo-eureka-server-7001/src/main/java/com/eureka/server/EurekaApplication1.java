package com.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}
)
// 表示自己是Server不是客户端
@EnableEurekaServer
public class EurekaApplication1 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication1.class, args);
    }
}
