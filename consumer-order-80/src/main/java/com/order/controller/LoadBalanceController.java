package com.order.controller;

import com.order.loadbalancer.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class LoadBalanceController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancer loadBalancer;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("consumer/loadBalance")
    public String loadBalance() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        ServiceInstance serviceInstance = loadBalancer.loadBalace(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri + "/loadBalance", String.class);
    }
}
