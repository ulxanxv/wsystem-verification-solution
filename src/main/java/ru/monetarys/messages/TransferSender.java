package ru.monetarys.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.monetarys.messages.entities.Transfer;
import ru.monetarys.integration.ApplicationProperties;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferSender {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public void sendMessage(Transfer request) {
        request.getHeader().setMessageId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                properties.getMqTransferProperties().getInExchangeName(),
                properties.getMqTransferProperties().getRoutingKey(),
                request
        );
    }

}