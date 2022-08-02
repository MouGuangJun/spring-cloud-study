package com.order.controller;

import com.base.utils.SerialHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试自动流水号的获取
 */
@RestController
public class SerialController {

    @Autowired
    private SerialHelper serialHelper;

    @GetMapping("/serial")
    public String getSerial() {
        return serialHelper.getCurrentSerial("BUSINESS_APPLY", "SERIALNO");
    }
}
