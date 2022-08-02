package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
// TODO 学习搭建zookeeper集群
public class OrderZkController {
    @Autowired
    private RestTemplate restTemplate;

    // 直连的方式
    // public static final String PAYMENT_URL = "http://localhost:8003/";// zookeeper的payment服务地址

    // 使用zookeeper提供寻找服务提供方的方式
    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @RequestMapping("zk")
    public String paymentZk() {
        return restTemplate.postForObject(INVOKE_URL + "/payment/zk", null, String.class);
    }
}
