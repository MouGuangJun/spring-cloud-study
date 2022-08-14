package com.stream.config;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义的消息通道接口，默认消息通道接口{@link org.springframework.cloud.stream.messaging.Processor}
 */
public interface CustomProcessor {
    String OUTPUT = "custom_output";

    @Output(OUTPUT)
    MessageChannel output();
}
