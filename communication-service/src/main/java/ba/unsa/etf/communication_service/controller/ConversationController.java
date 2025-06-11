package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
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
@RequestMapping("api/conversation")
@AllArgsConstructor
public class ConversationController {
  private final ConversationService conversationService;

  @GetMapping
  public ResponseEntity<Page<ConversationDTO>> getAll(Pageable pageable) {
    return ResponseEntity.ok(conversationService.findAll(pageable));
  }

  @GetMapping(params = {"id"})
  public ResponseEntity<ConversationDTO> getById(@RequestParam(required = true) Long id) {
    return conversationService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(params = {"name"})
  public ResponseEntity<Page<ConversationDTO>> getByName(
      @RequestParam(required = true) String name, Pageable pageable) {
    return ResponseEntity.ok(conversationService.findByName(name, pageable));
  }

  @GetMapping(params = {"userId"})
  public ResponseEntity<Page<ConversationDTO>> getByUserId(
      @RequestParam(required = true) Long userId, Pageable pageable) {
    return ResponseEntity.ok(conversationService.findByUserId(userId, pageable));
  }

  @PutMapping("/{conversationId}/link/user/{userId}")
  public ResponseEntity<Void> linkWithUser(
      @PathVariable Long userId, @PathVariable Long conversationId) {
    try {
      conversationService.linkWithUser(userId, conversationId);

      return ResponseEntity.ok().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().header("error", e.getMessage()).build();
    }
  }

  @PutMapping("/{conversationId}/unlink/user/{userId}")
  public ResponseEntity<Void> unlinkWithUser(
      @PathVariable Long userId, @PathVariable Long conversationId) {
    try {
      conversationService.unlinkWithUser(userId, conversationId);

      return ResponseEntity.ok().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().header("error", e.getMessage()).build();
    }
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
    boolean deleted = conversationService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
