package ba.unsa.etf.content_service.service;

import ba.unsa.etf.content_service.dto.CommentDto;
import ba.unsa.etf.content_service.entity.Comment;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.mapper.CommentMapper;
import ba.unsa.etf.content_service.repository.CommentRepository;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<CommentDto> getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(CommentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByUser(User user) {
        return commentRepository.findByUser(user)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostdate(LocalDate postdate) {
        return commentRepository.findByPostdate(postdate)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByTextContaining(String text) {
        return commentRepository.findByTextContaining(text)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = CommentMapper.toEntity(commentDto);

        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + commentDto.getPostId()));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + commentDto.getUserId()));

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toDto(savedComment);
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();

            Post post = postRepository.findById(commentDto.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found with ID: " + commentDto.getPostId()));
            User user = userRepository.findById(commentDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + commentDto.getUserId()));

            comment.setPost(post);
            comment.setUser(user);
            comment.setText(commentDto.getText());
            comment.setPostdate(commentDto.getPostdate());

            Comment updatedComment = commentRepository.save(comment);
            return CommentMapper.toDto(updatedComment);
        }
        return null;
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
