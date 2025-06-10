package ba.unsa.etf.user_management_service.rabbitmq.service;

import ba.unsa.etf.user_management_service.rabbitmq.model.SagaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class SagaServiceB {

    private final RabbitTemplate rabbitTemplate;
    private final AtomicLong orderId = new AtomicLong(1);

    public SagaServiceB(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "api1-consumer-queue")
    public void handleApi1(SagaMessage message) {
        try {
            log.info("API1 invoked");
            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key",
                    new SagaMessage("success", orderId.getAndIncrement()));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key", new SagaMessage("fail", null));
        }
    }

    @RabbitListener(queues = "api2-consumer-queue")
    public void handleApi2(SagaMessage message) {
        Long orderId = message.getOrderId();
        try {
            log.info("API2 invoked");
            Thread.sleep(5000);
            throw new Exception("help");
            // rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
            //        new SagaMessage("success", orderId));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
                    new SagaMessage("fail", orderId));
        }
    }

    @RabbitListener(queues = "compensate-api1-queue")
    public void handleCompensateApi1Request(SagaMessage message) {
        try {
            log.info("Compensating API1");
            //rollback
        } catch (Exception e) {
            System.out.println("API1 Compensation Failed");
        }
    }

    @RabbitListener(queues = "compensate-api2-queue")
    public void handleCompensateApi2Request(SagaMessage message) {
        try {
            log.info("Compensating API2");
            //rollback
        } catch (Exception e) {
            System.out.println("API2 Compensation Failed");
        }
        handleCompensateApi1Request(message);
    }
}
