package com.stream.controller;

import com.stream.service.CustomMessageService;
import com.stream.service.MessageServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageServerController {

    @Autowired
    private MessageServerService messageServerService;

    @Autowired
    private CustomMessageService customMessageService;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        return messageServerService.send();
    }

    @GetMapping("/sendCustomMessage")
    public String sendCustomMessage() {
        return customMessageService.send();
    }

}
