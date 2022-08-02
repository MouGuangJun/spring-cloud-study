package com.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 该注解用于向使用zookeeper或者consul作为注册中心时注册服务
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.payment.mapper"})
public class PaymentAppliation8003 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAppliation8003.class, args);
    }
}
