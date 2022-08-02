package com.hystrix.service;

import org.springframework.stereotype.Component;

/**
 * 订单系统调用支付系统的所有服务降级处理
 */
@Component
public class HystrixOrderFallbackService implements HystrixOrderService {
    @Override
    public String paymentInfo_OK(Integer id) {

        /*return "------------HystrixOrderFallbackService fallback paymentInfo_OK";*/
        return null;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        /*return "------------HystrixOrderFallbackService fallback paymentInfo_TimeOut";*/
        return null;
    }
}
