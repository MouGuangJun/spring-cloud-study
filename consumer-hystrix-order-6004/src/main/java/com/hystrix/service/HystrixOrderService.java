package com.hystrix.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLOUD-PRODIVER-HYSTRIX-PAYMENT", fallback = HystrixOrderFallbackService.class)
public interface HystrixOrderService {

    // 正常服务
    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    // 超时服务
    @GetMapping("/payment/hystrix/timeOut/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
