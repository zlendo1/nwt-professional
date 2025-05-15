package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component; // Add this annotation

@Component // Make PostMapper a Spring-managed bean
public class PostMapper {

  // Mapira pojedinačni Post entitet u PostDto
  public PostDto toDto(Post post) { // Remove static
    if (post == null) {
      return null;
    }

    PostDto postDto = new PostDto();
    postDto.setPostId(post.getPostId());
    postDto.setUserId(post.getUser().getId());
    postDto.setPostDate(post.getPostDate());
    postDto.setText(post.getText());
    postDto.setStatus(post.getStatus());

    return postDto;
  }

  // Mapira listu Post entiteta u listu PostDto
  public List<PostDto> toDto(List<Post> posts) { // Remove static
    return posts.stream()
        .map(this::toDto) // Use instance method
        .collect(Collectors.toList());
  }

  // Mapira PostDto u Post entitet
  public Post fromDto(PostDto postDto) { // Remove static
    if (postDto == null) {
      return null;
    }

    Post post = new Post();
    post.setPostId(postDto.getPostId());
    // Ovdje bi trebalo dodati logiku za postavljanje User entiteta na temelju postDto.getUserId()
    // You'll likely need to inject a UserService or UserRepository here
    post.setPostDate(postDto.getPostDate());
    post.setText(postDto.getText());
    post.setStatus(postDto.getStatus());

    return post;
  }
}
