package com.sleuth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SleuthController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/server/getServerPort")
    public String getServerPort() {
        return "this server port is:" + serverPort;
    }
}
