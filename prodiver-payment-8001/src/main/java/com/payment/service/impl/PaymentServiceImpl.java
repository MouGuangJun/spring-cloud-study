package com.payment.service.impl;

import com.base.model.ResultDataDto;
import com.base.model.ResultDto;
import com.base.utils.DateUtils;
import com.base.utils.ReflectUtils;
import com.payment.mapper.PaymentMapper;
import com.payment.service.PaymentService;
import com.service.dto.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 支付实现类
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public ResultDto addPayment(Payment paymentDto) {
        Payment payment = ReflectUtils.copyEntityIgnoreCase(paymentDto, Payment.class);
        ResultDto resultDto = new ResultDto(true, "支付信息保存成功！");
        payment.setInputDate(DateUtils.getRightDate());
        payment.setUpdateDate(DateUtils.getRightDate());
        paymentMapper.insert(payment);
        return resultDto;
    }

    @Override
    public ResultDataDto<Payment> getPaymentById(Long id) {
        ResultDataDto<Payment> resultDataDto = new ResultDataDto<>(true);
        resultDataDto.setData(ReflectUtils.copyEntityIgnoreCase(paymentMapper.selectById(id), Payment.class));
        resultDataDto.getData().setNote(serverPort);
        return resultDataDto;
    }
}
