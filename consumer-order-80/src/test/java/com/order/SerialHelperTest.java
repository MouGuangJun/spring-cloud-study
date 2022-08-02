package com.order;

import com.base.utils.SerialHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SerialHelperTest {
    @Autowired
    private SerialHelper serialHelper;

    @Test
    public void testRunSerial() {

        for (int i = 0; i < 100; i++) {
            System.out.println(serialHelper.getCurrentSerial("BUSINESS_APPLY", "SERIALNO"));
        }
    }
}
