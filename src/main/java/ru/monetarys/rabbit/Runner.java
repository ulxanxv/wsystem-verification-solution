package ru.monetarys.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.monetarys.MonetarysApplication;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(MonetarysApplication.TOPIC_EXCHANGE_NAME, "test.route.xaz", "Testing RabbitMQ");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
