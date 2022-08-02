package com.feign.controller;

import com.base.model.ResultDataDto;
import com.feign.service.OpenFeignService;
import com.service.dto.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private OpenFeignService openFeignService;

    @GetMapping("consumer/getPayment/{id}")
    public ResultDataDto<Payment> getPaymentById(@PathVariable("id") Long id) {
        return openFeignService.getPaymentById(id);
    }

    @GetMapping("consumer/timeOut")
    public String timeOut() {
        return openFeignService.timeOut();
    }
}
