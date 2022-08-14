package com.stream.service.impl;

import com.stream.config.CustomProcessor;
import com.stream.service.CustomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@EnableBinding(value = {CustomProcessor.class})
public class CustomMessageServiceImpl implements CustomMessageService {

    @Autowired
    private CustomProcessor customProcessor;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString() + "---------custom---------";
        customProcessor.output().send(MessageBuilder.withPayload(serial).build());

        return serial;
    }
}
