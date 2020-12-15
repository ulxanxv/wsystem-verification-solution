package ru.monetarys.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.monetarys.messages.entities.Transfer;

@Getter
@Service
@RequiredArgsConstructor
public class TransferConsumer {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${application.mq-transfer-properties.queue-name}")
    public void receiveTransfer(Message message) {
        Transfer transfer = ((Transfer) rabbitTemplate.getMessageConverter().fromMessage(message));
        // TODO: something...
    }

}
