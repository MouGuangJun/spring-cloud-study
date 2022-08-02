package com.payment.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置文件
 */
@Configuration
@MapperScan("com.payment.mapper")
public class MybatisConfig {
}
