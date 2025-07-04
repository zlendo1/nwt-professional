package ba.unsa.etf.communication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommunicationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommunicationServiceApplication.class, args);
  }
}
