package com.payment.controller;

import com.base.model.ResultDataDto;
import com.base.model.ResultDto;
import com.payment.service.PaymentService;
import com.service.dto.payment.Payment;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 新增数据记录
     */
    @PostMapping("/addPayment")
    public ResultDto addPayment(@RequestBody Payment payment) {
        return paymentService.addPayment(payment);
    }

    /**
     * 查询数据记录
     * <p>
     * PathVariable：请求路径上的参数，路径上多个参数用/隔开
     * 访问时：localhost:8001/payment/getPayment/1/music
     */
    @GetMapping("/getPayment/{id}/{more}")
    public ResultDataDto<Payment> getPaymentById(@PathVariable("id") Long id, @PathVariable("more") String more) {
        return paymentService.getPaymentById(id);
    }

    /**
     * 模拟业务超时的情况
     */
    @SneakyThrows
    @GetMapping("/timeOut")
    public String timeOut() {
        TimeUnit.SECONDS.sleep(3);
        return serverPort;
    }
}
