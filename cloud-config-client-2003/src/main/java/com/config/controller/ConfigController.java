package com.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
    @Value("${server.port}")
    private String serverPort;

    @Value("${datasource.driver}")
    private String datasourceDriver;

    @GetMapping("/getDatasourceDriver")
    public String getDatasourceDriver() {
        return "server.port=" + serverPort + ", " + datasourceDriver;
    }
}
