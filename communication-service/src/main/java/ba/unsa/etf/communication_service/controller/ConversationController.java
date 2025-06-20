package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/conversation")
@AllArgsConstructor
public class ConversationController {
  private final ConversationService conversationService;

  @GetMapping
  public ResponseEntity<Page<ConversationDTO>> getAll(Pageable pageable) {
    return ResponseEntity.ok(conversationService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ConversationDTO> getById(@PathVariable Long id) {
    return conversationService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/userId/{userId}")
  public ResponseEntity<Page<ConversationDTO>> getByUserId(
      @PathVariable Long userId, Pageable pageable) {
    return ResponseEntity.ok(conversationService.findByUserId(userId, pageable));
  }

  @PostMapping
  public ResponseEntity<ConversationDTO> create(@Valid @RequestBody CreateConversationDTO dto) {
    return ResponseEntity.ok(conversationService.create(dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean deleted = conversationService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
