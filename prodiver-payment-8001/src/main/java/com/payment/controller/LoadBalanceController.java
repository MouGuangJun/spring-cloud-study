package com.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手写负载均衡服务端逻辑
 */
@RestController
public class LoadBalanceController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("loadBalance")
    public String getServerPort() {
        return serverPort;
    }
}
