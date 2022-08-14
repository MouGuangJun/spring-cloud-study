package com.sleuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SleuthController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/client/getServerPort")
    public String getServerPort() {
        return restTemplate.getForObject("http://CLOUD-SLEUTH-SERVER/server/getServerPort", String.class);
    }
}
