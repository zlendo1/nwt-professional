package ba.unsa.etf.content_service.controller;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.mapper.PostMapper;
import ba.unsa.etf.content_service.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

  private final PostService postService;
  private final PostMapper postMapper; // We still need the mapper for GET requests

  // GET all posts
  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts() {
    List<PostDto> posts = postService.getAllPosts();
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  // GET post by ID
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
    Optional<PostDto> post = postService.getPostById(id);
    return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // POST create a new post
  @PostMapping
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    try {
      PostDto createdPost = postService.createPost(postDto);
      return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      throw e;
      // Catch the "User not found" exception from the service
      // errorResponse.put("message", e.getMessage());
      // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    }
  }

  // DELETE post by ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
    postService.deletePost(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
