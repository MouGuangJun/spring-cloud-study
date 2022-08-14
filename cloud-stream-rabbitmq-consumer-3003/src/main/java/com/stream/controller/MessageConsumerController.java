package com.stream.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Sink.class)
public class MessageConsumerController {
    @Value("${server.port}")
    private String serverPort;


    @StreamListener(Sink.INPUT)
    public void intput(Message<String> message) {
        System.out.println("接收到消息：" + message.getPayload() + "\t port：" + serverPort);
    }
}
