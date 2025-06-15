package ba.unsa.etf.content_service.client;

import ba.unsa.etf.content_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management-service")
public interface UserManagementClient {
  // api iz controlera iz usermanagement servisa
  @GetMapping("/api/user/{id}")
  UserDto getUserById(@PathVariable("id") Long id);
}
