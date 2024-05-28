package com.like.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Exchange delayExchange() {
        Map<String, Object> args = new HashMap<>(2);
        //  x-delayed-type 声明 延迟队列Exchange的类型
        args.put("x-delayed-type", "direct");
        // 设置名字 交换机类型 持久化 不自动删除
        return new CustomExchange("dev-delayed-exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable("dev-delayed-queue").build();
    }
}
