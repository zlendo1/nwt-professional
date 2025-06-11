package ba.unsa.etf.user_management_service.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SagaMessage {
    private String status;
    private Long orderId;
}