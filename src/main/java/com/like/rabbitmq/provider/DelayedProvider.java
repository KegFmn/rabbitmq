package com.like.rabbitmq.provider;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class DelayedProvider implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(Integer delayedTime, String routingKey, String messageBody) {

        rabbitTemplate.setConfirmCallback(this);

        // 指定之前定义的延迟交换机名 与路由键名
        rabbitTemplate.convertAndSend("dev-delayed-exchange", routingKey, messageBody, message -> {
            // 延迟时间单位是毫秒
            message.getMessageProperties().setDelay(delayedTime);
            System.out.println("消息发送时间:" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")) +
                    "消息内容:" + messageBody);
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("确认回调-相关数据："+ correlationData);
        System.out.println("确认回调-确认情况："+ ack);
        System.out.println("确认回调-原因："+ cause);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("返回回调-消息：" + returned.getMessage());
        System.out.println("返回回调-回应码："+ returned.getReplyCode());
        System.out.println("返回回调-回应信息："+ returned.getReplyText());
        System.out.println("返回回调-交换机："+ returned.getExchange());
        System.out.println("返回回调-路由键："+ returned.getRoutingKey());
    }
}
