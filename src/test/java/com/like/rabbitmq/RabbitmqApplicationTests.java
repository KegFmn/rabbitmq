package com.like.rabbitmq;

import com.like.rabbitmq.provider.DelayedProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
class RabbitmqApplicationTests {

    @Resource
    private DelayedProvider delayedProvider;

    @Test
    void contextLoads() {
        delayedProvider.send(5000, "order", String.format("延迟消息-%s", LocalDateTime.now().plusSeconds(5)));
    }

}
