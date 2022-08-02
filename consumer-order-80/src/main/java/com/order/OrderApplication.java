package com.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*@SpringBootApplication(
        // 不需要加载数据库，防止报错：Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
        exclude = {DataSourceAutoConfiguration.class}
)*/
@SpringBootApplication
@ComponentScan(basePackages = {"com.order", "com.base"})
// @RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
