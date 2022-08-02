package com.order.controller;

import com.base.model.ResultDataDto;
import com.base.model.ResultDto;
import com.service.dto.payment.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001/";// payment服务地址
    // 集群的时候需要修改为微服务的名称
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";// payment服务地址

    @Autowired
    private RestTemplate restTemplate;

    // 消费者远程调用支付服务
    @PostMapping("/addPayment")
    public ResultDto addPayment(@RequestBody Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/addPayment", payment, ResultDto.class);
    }


    @PostMapping("/addPaymentEntity")
    public ResultDto addPaymentEntity(@RequestBody Payment payment) {
        return restTemplate.postForEntity(PAYMENT_URL + "/payment/addPayment", payment, ResultDto.class).getBody();
    }

    /**
     * 返回对象为响应体中数据转化成的对象，基本可以理解为json
     */
    @GetMapping("/getPayment/{id}")
    public ResultDataDto getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPayment/" + id + "/music", ResultDataDto.class);
    }

    /**
     * 返回对象为ResponseEntity对象，包含响应中一些重要信息，比如响应头、响应状态码、响应体等
     */
    @GetMapping("/getForPaymentEntity/{id}")
    public ResultDataDto getPaymentEntity(@PathVariable("id") Long id) {
        ResponseEntity<ResultDataDto> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPayment/" + id + "/music", ResultDataDto.class);
        log.info("调用远程服务返回结果：{}", entity);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }

        return new ResultDataDto(false, "远程服务调用失败：" + entity.getStatusCode());
    }
}