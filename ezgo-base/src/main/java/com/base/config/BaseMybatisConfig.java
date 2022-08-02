package com.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis相关配置
 */
@Configuration
@MapperScan("com.base.mapper")
public class BaseMybatisConfig {
}
