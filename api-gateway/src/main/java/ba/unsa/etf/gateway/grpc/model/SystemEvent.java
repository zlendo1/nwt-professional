package ba.unsa.etf.gateway.grpc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "system_events")
public class SystemEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "timestamp")
    private String timestamp;
    @Column(name = "microservice_name")
    private String microserviceName;
    @Column(name = "event_user")
    private String user;
    @Column(name = "action_type")
    private String actionType;
    @Column(name = "resource_name")
    private String resourceName;
    @Column(name = "response_type")
    private String responseType;
}
