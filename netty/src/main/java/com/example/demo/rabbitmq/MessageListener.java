package com.example.demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @RabbitListener(queues = "my-queue")
    public void receiveMessage(String message) {
        log.info("接收到消息: {}", message);
    }
}


