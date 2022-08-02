package com.hystrix.controller;

import com.hystrix.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    // 正常的业务场景
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("-------------执行结果成功：" + result);

        return result;
    }

    // 模拟超时的业务场景
    @GetMapping("/payment/hystrix/timeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("-------------执行结果失败：" + result);

        return result;
    }


    // --------------服务熔断--------------
    @GetMapping("/payment/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("-------------执行结果：" + result);

        return result;
    }

    // --------------服务限流--------------
    // 通过信号量进行限流
    @GetMapping("/payment/hystrix/flowLimitSemaphore")
    public String paymentFlowLimitSemaphore() {
        return paymentService.paymentFlowLimitSemaphore();
    }

    // 通过线程数进行限流
    @GetMapping("/payment/hystrix/flowLimitThread")
    public String paymentFlowLimitThread() {
        return paymentService.paymentFlowLimitThread();
    }
}
