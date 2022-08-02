package com.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OrderAppliation6001 {
    public static void main(String[] args) {
        SpringApplication.run(OrderAppliation6001.class, args);
    }
}
