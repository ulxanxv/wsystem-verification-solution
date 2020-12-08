package ru.monetarys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.monetarys.services.clientprofile.ApplicationProperties;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

    private final ApplicationProperties properties;

    private Queue queue() {
        return new Queue(properties.getMqTransferProperties().getQueueName(), false);
    }

    private TopicExchange outExchange() {
        return new TopicExchange(properties.getMqTransferProperties().getOutExchangeName());
    }

    @Bean
    public Binding declareBindingOut() {
        return BindingBuilder
                .bind(queue())
                .to(outExchange())
                .with(properties.getMqTransferProperties().getRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
