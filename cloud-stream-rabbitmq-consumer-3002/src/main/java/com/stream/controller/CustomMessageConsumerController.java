package com.stream.controller;

import com.stream.config.CustomProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(value = {CustomProcessor.class, Source.class})
public class CustomMessageConsumerController {
    @Value("${server.port}")
    private String serverPort;


    @StreamListener(CustomProcessor.INPUT)
    @SendTo(Source.OUTPUT)// 消息处理之后，转发到input消息通道
    public String intput(Message<String> message) {
        System.out.println("CustomMessageConsumerController接收到消息：" + message.getPayload() + "\t port：" + serverPort);
        return message.getPayload();
    }
}
