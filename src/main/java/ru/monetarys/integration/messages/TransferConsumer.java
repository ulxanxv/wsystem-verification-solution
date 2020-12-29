package ru.monetarys.integration.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.monetarys.integration.messages.entities.TransferFeedback;
import ru.monetarys.logic.TransferManager;

@Getter
@Service
@RequiredArgsConstructor
public class TransferConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final TransferManager transferManager;

    @RabbitListener(queues = "${application.mq-transfer-properties.queue-name}")
    public void receiveTransfer(Message message) {
        TransferFeedback transferFeedback = ((TransferFeedback) rabbitTemplate.getMessageConverter().fromMessage(message));
        transferManager.updateTransferStatus(transferFeedback);
    }

}
