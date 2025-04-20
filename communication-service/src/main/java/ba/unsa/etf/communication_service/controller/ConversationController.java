package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
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
@RequestMapping("api/conversation")
@AllArgsConstructor
public class ConversationController {
  private final ConversationService conversationService;

  @GetMapping
  public ResponseEntity<List<ConversationDTO>> getAllConversations() {
    return ResponseEntity.ok(conversationService.findAll());
  }

  @GetMapping(params = {"id"})
  public ResponseEntity<ConversationDTO> getConversationById(
      @RequestParam(required = true) Long id) {
    return conversationService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(params = {"name"})
  public List<ConversationDTO> getConversationsByName(@RequestParam(required = true) String name) {
    return conversationService.findByName(name);
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<UserDTO>> getConversationUsers(@PathVariable Long id) {
    return conversationService
        .findUsersById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ConversationDTO> create(@Valid @RequestBody CreateConversationDTO dto) {
    return ResponseEntity.ok(conversationService.create(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ConversationDTO> update(
      @PathVariable Long id, @Valid @RequestBody CreateConversationDTO dto) {
    return conversationService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    conversationService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
