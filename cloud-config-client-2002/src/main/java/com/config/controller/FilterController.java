package com.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/filter")
    public String filter() {
        return serverPort;
    }


    @GetMapping("/docker")
    public String docker() {
        return "docker " + serverPort;
    }
}
