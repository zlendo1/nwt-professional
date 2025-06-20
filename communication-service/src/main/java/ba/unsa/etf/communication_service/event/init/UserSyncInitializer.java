package ba.unsa.etf.communication_service.event.init;

import ba.unsa.etf.communication_service.dto.user.ManagerUserDTO;
import ba.unsa.etf.communication_service.entity.User;
import ba.unsa.etf.communication_service.mapper.UserMapper;
import ba.unsa.etf.communication_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
@Slf4j
public class UserSyncInitializer {

  private UserRepository userRepository;
  private UserMapper userMapper;
  private DiscoveryClient discoveryClient;

  @PostConstruct
  public void syncUsersOnStartup() {
    try {
      if (!userRepository.findAll().isEmpty()) {
        return;
      }

      String serviceName = "user-management-service";
      String usersEndpoint = "/api/user";

      List<String> instances =
          discoveryClient.getInstances(serviceName).stream()
              .map(si -> si.getUri().toString())
              .toList();

      if (instances.isEmpty()) {
        log.warn("No instances of user-management-service found via Eureka.");
        return;
      }

      String baseUrl = instances.get(0);
      log.info("Attempting to sync users from: {}", baseUrl + usersEndpoint);

      WebClient webClient = WebClient.create(baseUrl);
      List<User> users =
          webClient
              .get()
              .uri(usersEndpoint)
              .retrieve()
              .bodyToFlux(ManagerUserDTO.class)
              .map(userMapper::fromManagerUserDTO)
              .collectList()
              .block();

      if (users != null) {
        userRepository.saveAll(users);
        log.info("Synchronized {} users from user-management-service.", users.size());
      }
    } catch (Exception e) {
      log.error("Failed to sync users on startup", e);
    }
  }
}
