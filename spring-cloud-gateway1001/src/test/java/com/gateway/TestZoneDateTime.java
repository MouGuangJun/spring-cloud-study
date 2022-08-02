package com.gateway;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class TestZoneDateTime {

    @Test
    public void testZoneTime() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
