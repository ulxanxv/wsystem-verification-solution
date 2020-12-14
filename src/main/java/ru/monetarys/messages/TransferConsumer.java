package ru.monetarys.messages;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.monetarys.messages.entities.TransferRequest;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class TransferConsumer {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${application.mq-transfer-properties.queue-name}")
    public void receiveTransfer(Message message) {
        TransferRequest transferRequest = ((TransferRequest) rabbitTemplate.getMessageConverter().fromMessage(message));
        // TODO: something...
    }

}
