package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/conversation")
@AllArgsConstructor
public class ConversationController {
  private final ConversationService conversationService;

  @GetMapping
  public List<ConversationDTO> getAllConversations() {
    return conversationService.findAll();
  }

  @GetMapping(params = {"id"})
  public ResponseEntity<ConversationDTO> getConversationByName(
      @RequestParam(required = true) Long id) {
    return conversationService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
