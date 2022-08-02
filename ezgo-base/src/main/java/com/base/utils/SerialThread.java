package com.base.utils;

public class SerialThread extends Thread {
    private SerialHelper serialHelper;

    public SerialThread(SerialHelper serialHelper) {
        this.serialHelper = serialHelper;
    }

    @Override
    public void run() {
        String currentSerial = serialHelper.getCurrentSerial("BUSINESS_APPLY", "SERIALNO");
        System.out.println(currentSerial);
    }
}
