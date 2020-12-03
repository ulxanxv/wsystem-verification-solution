package ru.monetarys.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class TransferSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        // TODO: sending message...
    }

}