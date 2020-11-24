package ru.monetarys.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.monetarys.config.RabbitBeans;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(RabbitBeans.TOPIC_EXCHANGE_NAME, "test.route.xaz", "Testing RabbitMQ");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
