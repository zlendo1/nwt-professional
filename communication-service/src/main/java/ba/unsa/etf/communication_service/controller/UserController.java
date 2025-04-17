package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.ConversationDTO;
import ba.unsa.etf.communication_service.dto.UserDTO;
import ba.unsa.etf.communication_service.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public List<UserDTO> getAllUsers() {
    return userService.findAll();
  }

  @GetMapping(params = {"id", "username", "email"})
  public ResponseEntity<UserDTO> getUser(
      @RequestParam(required = false) Long id,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String email) {
    if (id != null) {
      return userService
          .findById(id)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    }

    if (username != null) {
      return userService
          .findByUsername(username)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    }

    if (email != null) {
      return userService
          .findByEmail(email)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    }

    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/{id}/conversations")
  public ResponseEntity<Set<ConversationDTO>> getUserConversations(@PathVariable Long id) {
    return userService
        .findConversationsById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
