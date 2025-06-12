package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.user.CreateUserDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<Page<UserDTO>> getAll(Pageable pageable) {
    return ResponseEntity.ok(userService.findAll(pageable));
  }

  @GetMapping(params = {"id"})
  public ResponseEntity<UserDTO> getById(@RequestParam(required = true) Long id) {
    return userService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(params = {"username"})
  public ResponseEntity<UserDTO> getByUsername(@RequestParam(required = true) String username) {
    return userService
        .findByUsername(username)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(params = {"email"})
  public ResponseEntity<UserDTO> getByEmail(@RequestParam(required = true) String email) {
    return userService
        .findByEmail(email)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(params = {"conversationId"})
  public ResponseEntity<Page<UserDTO>> getByConversationId(
      @RequestParam(required = true) Long conversationId, Pageable pageable) {
    return ResponseEntity.ok(userService.findByConversationId(conversationId, pageable));
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
    boolean deleted = userService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
