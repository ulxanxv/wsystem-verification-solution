package ru.monetarys.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.monetarys.integration.messages.TransferConsumer;

import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TransferFeedbackConsumerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Spy
    @InjectMocks
    private TransferConsumer transferConsumer;

    @Test
    public void receiveSomeMessage() {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(
                new ObjectMapper().findAndRegisterModules()
        );

        when(transferConsumer.getRabbitTemplate().getMessageConverter())
                .thenReturn(messageConverter);

        transferConsumer.receiveTransfer(
                messageConverter.toMessage(TransferSenderUtil.getTransferRequestWithData(), null)
        );

        verify(transferConsumer, times(1)).receiveTransfer(any());
    }

}
