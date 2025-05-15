package ba.unsa.etf.content_service.controller;

import ba.unsa.etf.content_service.dto.AnalyticsDto;
import ba.unsa.etf.content_service.entity.Analytics;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.mapper.AnalyticsMapper;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.service.AnalyticsService;
import jakarta.validation.Valid;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

  private final AnalyticsService analyticsService;
  private final AnalyticsMapper analyticsMapper;
  private final PostRepository postRepository;

  // GET all analytics
  @GetMapping
  public ResponseEntity<List<AnalyticsDto>> getAllAnalytics() {
    List<Analytics> analyticsList = analyticsService.getAllAnalytics();
    List<AnalyticsDto> analyticsDtos = analyticsMapper.toDTO(analyticsList);
    return ResponseEntity.ok(analyticsDtos);
  }

  // GET analytics by ID
  @GetMapping("/{id}")
  public ResponseEntity<AnalyticsDto> getAnalyticsById(@PathVariable("id") Long id) {
    Optional<Analytics> analytics = analyticsService.getAnalyticsById(id);
    return analytics
        .map(value -> ResponseEntity.ok(analyticsMapper.toDTO(value)))
        .orElse(ResponseEntity.notFound().build());
  }

  // POST create new analytics
  @PostMapping
  public ResponseEntity<?> createAnalytics(
      @Valid @RequestBody AnalyticsDto analyticsDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, Object> response = new HashMap<>();
      Map<String, String> messages = new HashMap<>();

      bindingResult
          .getFieldErrors()
          .forEach(error -> messages.put(error.getField(), error.getDefaultMessage()));

      response.put("error", "validation");
      response.put("messages", messages);

      return ResponseEntity.badRequest().body(response);
    }

    Post post =
        postRepository
            .findById(analyticsDto.getPostId())
            .orElseThrow(
                () -> new RuntimeException("Post not found with ID: " + analyticsDto.getPostId()));

    Analytics analytics = analyticsMapper.toEntity(analyticsDto);
    analytics.setPost(post);

    Analytics createdAnalytics = analyticsService.createAnalytics(analytics);
    return ResponseEntity.status(HttpStatus.CREATED).body(analyticsMapper.toDTO(createdAnalytics));
  }

  // DELETE analytics by ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAnalytics(@PathVariable("id") Long id) {
    analyticsService.deleteAnalytics(id);
    return ResponseEntity.noContent().build();
  }
}
