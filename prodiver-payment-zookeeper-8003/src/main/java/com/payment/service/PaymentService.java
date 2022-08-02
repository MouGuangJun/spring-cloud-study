package com.payment.service;

import com.base.model.ResultDataDto;
import com.base.model.ResultDto;
import com.service.dto.payment.Payment;

/**
 * 支付接口
 */
public interface PaymentService {
    // 新增支付信息
    ResultDto addPayment(Payment payment);

    // 通过主键查询
    ResultDataDto<Payment> getPaymentById(Long id);
}
