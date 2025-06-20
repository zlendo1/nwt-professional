package ba.unsa.etf.communication_service.controller;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.service.MessageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message")
@AllArgsConstructor
@Tag(name = "Message", description = "Message management API")
public class MessageController {
  private final MessageService messageService;

  @GetMapping
  @Operation(
      summary = "Get all messages",
      description = "Retrieve a paginated list of all messages")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved messages",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
      })
  public ResponseEntity<Page<MessageDTO>> getAll(
      @Parameter(description = "Pagination information") Pageable pageable) {
    return ResponseEntity.ok(messageService.findAll(pageable));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get message by ID", description = "Retrieve a specific message by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved message",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "404", description = "Message not found")
      })
  public ResponseEntity<MessageDTO> getById(
      @Parameter(description = "ID of the message to retrieve") @PathVariable Long id) {
    return messageService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/conversationId/{conversationId}")
  @Operation(
      summary = "Get messages by conversation ID",
      description =
          "Retrieve all messages for a specific conversation, optionally filtered by user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved conversation messages",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
      })
  public ResponseEntity<Page<MessageDTO>> getByConversationId(
      @Parameter(description = "ID of the conversation whose messages to retrieve") @PathVariable
          Long conversationId,
      @Parameter(description = "Optional user ID to filter messages by user")
          @RequestParam(required = false)
          Long userId,
      @Parameter(description = "Pagination information") Pageable pageable) {
    if (userId != null) {
      return ResponseEntity.ok(
          messageService.findByUserAndConversationId(userId, conversationId, pageable));
    } else {
      return ResponseEntity.ok(messageService.findByConversationId(conversationId, pageable));
    }
  }

  @PostMapping
  @Operation(summary = "Create new message", description = "Create a new message in a conversation")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully created message",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User or conversation not found")
      })
  public ResponseEntity<MessageDTO> create(
      @Parameter(description = "Message creation data") @Valid @RequestBody CreateMessageDTO dto) {
    return ResponseEntity.ok(messageService.create(dto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete message", description = "Delete a message by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted message"),
        @ApiResponse(responseCode = "404", description = "Message not found")
      })
  public ResponseEntity<Boolean> delete(
      @Parameter(description = "ID of the message to delete") @PathVariable Long id) {
    boolean deleted = messageService.delete(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
