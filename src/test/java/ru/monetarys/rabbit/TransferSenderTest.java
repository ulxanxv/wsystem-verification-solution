package ru.monetarys.rabbit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.monetarys.messages.TransferSender;
import ru.monetarys.messages.entities.TransferRequest;
import ru.monetarys.services.clientprofile.ApplicationProperties;

import static org.mockito.Mockito.*;
import static ru.monetarys.rabbit.TransferSenderUtil.*;
import static ru.monetarys.rest.ClientProfileServiceImplUtil.getApplicationProperties;

@ExtendWith({SpringExtension.class})
public class TransferSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Spy
    @InjectMocks
    private TransferSender transferSender;

    private final ApplicationProperties properties = getApplicationProperties();

    @BeforeEach
    public void setup() {
        transferSender = new TransferSender(rabbitTemplate, properties);
    }

    @Test
    public void testSend() {
        transferSender.sendMessage(getTransferRequestWithData());
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(TransferRequest.class));
    }

}
