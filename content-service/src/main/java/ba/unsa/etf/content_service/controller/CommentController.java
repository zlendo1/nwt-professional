package ba.unsa.etf.content_service.controller;

import ba.unsa.etf.content_service.dto.CommentDto;
import ba.unsa.etf.content_service.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping
  public ResponseEntity<List<CommentDto>> getAllComments() {
    return ResponseEntity.ok(commentService.getAllComments());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
    return commentService
        .getCommentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto) {
    CommentDto created = commentService.createComment(commentDto);
    return ResponseEntity.status(201).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CommentDto> updateComment(
      @PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
    CommentDto updated = commentService.updateComment(id, commentDto);
    if (updated == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
    return ResponseEntity.noContent().build();
  }
}
