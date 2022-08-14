package com.stream.service.impl;

import com.stream.service.MessageServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;


@EnableBinding(value = {Source.class})// 定义消息的推送管道
public class MessageServerServiceImpl implements MessageServerService {
    @Autowired
    private MessageChannel output;// 消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());

        return serial;
    }
}
