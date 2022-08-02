package com.feign.service;

import com.base.model.ResultDataDto;
import com.service.dto.payment.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface OpenFeignService {

    @GetMapping("payment/getPayment/{id}/rap")
    ResultDataDto<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("payment/timeOut")
    String timeOut();
}
