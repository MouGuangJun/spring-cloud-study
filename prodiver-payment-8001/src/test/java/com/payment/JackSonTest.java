package com.payment;

import com.base.utils.DateUtils;
import com.base.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.dto.payment.Payment;
import org.junit.jupiter.api.Test;

public class JackSonTest {
    @Test
    public void obj2Json() throws JsonProcessingException {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setInputDate(DateUtils.getRightDate());
        payment.setUpdateDate(DateUtils.getRightDate());
        payment.setSerialNo("BC202207010001");
        payment.setNote("obj2Json");
        System.out.println(JacksonUtils.obj2Jackson(payment));
    }

    @Test
    public void json2Obj() {
        Payment payment = JacksonUtils.jacksonFile2Obj("D:\\IDEAWorkSpace\\payment\\prodiver-payment-8001\\src\\main\\resources\\payment.json", Payment.class);
        System.out.println(payment);
    }
}
