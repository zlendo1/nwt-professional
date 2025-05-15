package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.mapper.PostMapper;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final PostMapper postMapper; // Inject the PostMapper

  @Autowired
  public PostService(
      PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postMapper = postMapper; // Initialize the injected PostMapper
  }

  @Transactional(readOnly = true)
  public List<PostDto> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts.stream()
        .map(postMapper::toDto) // Use the instance method
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<PostDto> getPostById(Long id) {
    return postRepository.findById(id).map(postMapper::toDto); // Use the instance method
  }

  @Transactional
  public PostDto createPost(PostDto postDto) {
    Post post = postMapper.fromDto(postDto); // Use the instance method

    // ⬇️ Log for debugging
    System.out.println("User ID in DTO: " + postDto.getUserId());

    // Fetch user from DB
    User user =
        userRepository
            .findById(postDto.getUserId())
            .orElseThrow(
                () -> new RuntimeException("User not found with ID: " + postDto.getUserId()));

    System.out.println("Fetched user from DB: " + user.getUsername());

    post.setUser(user); //  Set the fetched User

    Post savedPost = postRepository.save(post);
    return postMapper.toDto(savedPost); // Use the instance method
  }

  @Transactional
  public void deletePost(Long id) {
    postRepository.deleteById(id);
  }
}
