package com.like.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DelayedConsumer {

    @RabbitListener(queues = "dev-delayed-queue")
    public void testListenerDelayedMessage(Message message) {
        byte[] body = message.getBody();
        System.out.println("消息收到时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")) +
                " 消息内容:" + new String(body));
    }
}
