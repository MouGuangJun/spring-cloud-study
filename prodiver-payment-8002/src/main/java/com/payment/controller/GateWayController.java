package com.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 添加对GatWay网关测试的Controller
 */
@RestController
public class GateWayController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/gateway/getServer1")
    public String getServerPort1() {
        return serverPort + "：1";
    }

    @GetMapping("/gateway/getServer2")
    public String getServerPort2() {
        return serverPort + "：2";
    }
}
