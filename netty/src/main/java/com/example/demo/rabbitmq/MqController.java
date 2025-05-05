package com.example.demo.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {

    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("/send")
    public String send(@RequestParam String msg) {
        messageProducer.sendMessage(msg);
        return "ok";
    }
}

