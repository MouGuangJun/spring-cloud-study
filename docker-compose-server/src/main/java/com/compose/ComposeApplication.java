package com.compose;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.compose.mapper"})
public class ComposeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComposeApplication.class, args);
    }
}
