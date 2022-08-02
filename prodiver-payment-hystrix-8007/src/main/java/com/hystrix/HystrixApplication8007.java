package com.hystrix;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCircuitBreaker
@SuppressWarnings("unchecked")
public class HystrixApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication8007.class, args);
    }


    /**
     * 而配置服务监控默认路径
     */
    @Bean
    public ServletRegistrationBean getRegistrationBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                new HystrixMetricsStreamServlet());
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }
}
