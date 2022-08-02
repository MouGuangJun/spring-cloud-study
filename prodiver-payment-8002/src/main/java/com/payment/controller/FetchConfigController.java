package com.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 抓取配置信息的controller
 */
@RestController
@RequestMapping("/payment/system")
@Slf4j
public class FetchConfigController {

    @Autowired
    private DiscoveryClient discoveryClient;// 获取eureka注册中心上的相关信息


    @PostMapping("/discovery")
    public DiscoveryClient discovery() {
        List<String> services = discoveryClient.getServices();// 获取注册中心上的每一个服务名称
        services.forEach(c -> {
            log.info("elements:{}", c);

            // 获取每一个服务的实例
            List<ServiceInstance> instances = discoveryClient.getInstances(c);
            instances.forEach(sc ->
                    log.info(sc.getServiceId() + "\t" + sc.getHost() + "\t" + sc.getPort() + "\t" + sc.getUri())
            );
        });

        return discoveryClient;
    }
}
