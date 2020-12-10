package ru.monetarys.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.monetarys.config.RabbitMqConfiguration;
import ru.monetarys.messages.entities.TransferRequest;
import ru.monetarys.services.clientprofile.ApplicationProperties;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferSender {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public void sendMessage(TransferRequest request) {
        request.getHeader().setMessageId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                properties.getMqTransferProperties().getOutExchangeName(),
                properties.getMqTransferProperties().getRoutingKey(),
                request
        );
    }

}