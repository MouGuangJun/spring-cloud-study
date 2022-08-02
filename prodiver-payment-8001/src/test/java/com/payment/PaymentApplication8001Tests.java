package com.payment;

import com.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class PaymentApplication8001Tests {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testEzgoPayment() {
        System.out.println(paymentService.getPaymentById(1L));
    }
}
