package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.service.MessageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message")
@AllArgsConstructor
public class MessageController {
  private final MessageService messageService;

  @GetMapping
  public ResponseEntity<Page<MessageDTO>> getAll(Pageable pageable) {
    return ResponseEntity.ok(messageService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MessageDTO> getById(@PathVariable Long id) {
    return messageService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/conversationId/{conversationId}")
  public ResponseEntity<Page<MessageDTO>> getByConversationId(
      @PathVariable Long conversationId,
      @RequestParam(required = false) Long userId,
      Pageable pageable) {
    if (userId != null) {
      return ResponseEntity.ok(
          messageService.findByUserAndConversationId(userId, conversationId, pageable));
    } else {
      return ResponseEntity.ok(messageService.findByConversationId(conversationId, pageable));
    }
  }

  @PostMapping
  public ResponseEntity<MessageDTO> create(@Valid @RequestBody CreateMessageDTO dto) {
    return ResponseEntity.ok(messageService.create(dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    boolean deleted = messageService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
