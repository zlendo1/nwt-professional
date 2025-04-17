package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.mapper.PostMapper;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return PostMapper.toDto(posts);
    }

    @Transactional(readOnly = true)
    public Optional<PostDto> getPostById(Long id) {
        return postRepository.findById(id).map(PostMapper::toDto);
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.fromDto(postDto);

        // ⬇️ Log for debugging
        System.out.println("User ID in DTO: " + postDto.getUserId());

        // Fetch user from DB
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + postDto.getUserId()));

        System.out.println("Fetched user from DB: " + user.getUsername());

        post.setUser(user); // 👈 Set the fetched User

        Post savedPost = postRepository.save(post);
        return PostMapper.toDto(savedPost);
    }





    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
