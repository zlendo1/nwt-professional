package ba.unsa.etf.user_management_service.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    private static final String[] QUEUES = {"api1-producer", "api1-consumer", "api2-producer", "api2-consumer", "compensate-api1", "compensate-api2"};

    @Bean
    public Exchange sagaExchange() {
        return ExchangeBuilder.directExchange("saga-exchange").build();
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables declarables() {
        List<Declarable> declarables = new ArrayList<>();
        for (String queueName : QUEUES) {
            Queue queue = QueueBuilder.durable(queueName + "-queue").build();
            Binding binding = BindingBuilder.bind(queue).to(sagaExchange()).with(queueName + "-routing-key").noargs();
            declarables.add(queue);
            declarables.add(binding);
        }
        return new Declarables(declarables);
    }
}