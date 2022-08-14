package com.stream.config;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义的消息通道接口，默认消息通道接口{@link org.springframework.cloud.stream.messaging.Processor}
 */
public interface CustomProcessor {
    String INPUT = "custom_input";

    @Input(INPUT)
    SubscribableChannel input();
}
