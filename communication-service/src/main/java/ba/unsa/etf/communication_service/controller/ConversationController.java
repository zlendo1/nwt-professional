package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Conversation", description = "Conversation management API")
public class ConversationController {
  private final ConversationService conversationService;

  @GetMapping
  @Operation(
      summary = "Get all conversations",
      description = "Retrieve a paginated list of all conversations")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved conversations",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
      })
  public ResponseEntity<Page<ConversationDTO>> getAll(
      @Parameter(description = "Pagination information") Pageable pageable) {
    return ResponseEntity.ok(conversationService.findAll(pageable));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get conversation by ID",
      description = "Retrieve a specific conversation by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved conversation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ConversationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Conversation not found")
      })
  public ResponseEntity<ConversationDTO> getById(
      @Parameter(description = "ID of the conversation to retrieve") @PathVariable Long id) {
    return conversationService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/userId/{userId}")
  @Operation(
      summary = "Get conversations by user ID",
      description = "Retrieve all conversations for a specific user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved user conversations",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
      })
  public ResponseEntity<Page<ConversationDTO>> getByUserId(
      @Parameter(description = "ID of the user whose conversations to retrieve") @PathVariable
          Long userId,
      @Parameter(description = "Pagination information") Pageable pageable) {
    return ResponseEntity.ok(conversationService.findByUserId(userId, pageable));
  }

  @PostMapping
  @Operation(
      summary = "Create new conversation",
      description = "Create a new conversation between two users")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully created conversation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ConversationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "One or both users not found")
      })
  public ResponseEntity<ConversationDTO> create(
      @Parameter(description = "Conversation creation data") @Valid @RequestBody
          CreateConversationDTO dto) {
    return ResponseEntity.ok(conversationService.create(dto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete conversation", description = "Delete a conversation by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted conversation"),
        @ApiResponse(responseCode = "404", description = "Conversation not found")
      })
  public ResponseEntity<Void> delete(
      @Parameter(description = "ID of the conversation to delete") @PathVariable Long id) {
    boolean deleted = conversationService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
