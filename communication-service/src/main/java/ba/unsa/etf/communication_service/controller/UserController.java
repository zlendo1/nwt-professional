package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.user.CreateUserDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.findAll());
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
  public ResponseEntity<List<ConversationDTO>> getUserConversations(@PathVariable Long id) {
    return userService
        .findConversationsById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{userId}/link/conversation/{conversationId}")
  public ResponseEntity<Void> linkWithConversation(
      @PathVariable Long userId, @PathVariable Long conversationId) {
    try {
      userService.linkWithConversation(userId, conversationId);

      return ResponseEntity.ok().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().header("error", e.getMessage()).build();
    }
  }

  @PutMapping("/{userId}/unlink/conversation/{conversationId}")
  public ResponseEntity<Void> unlinkWithConversation(
      @PathVariable Long userId, @PathVariable Long conversationId) {
    try {
      userService.unlinkWithConversation(userId, conversationId);

      return ResponseEntity.ok().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().header("error", e.getMessage()).build();
    }
  }

  @PostMapping
  public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserDTO dto) {
    return ResponseEntity.ok(userService.create(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> update(
      @PathVariable Long id, @Valid @RequestBody CreateUserDTO dto) {
    return userService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
