package ba.unsa.etf.content_service.services;

import ba.unsa.etf.content_service.entity.Comment;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostdate(LocalDate postdate) {
        return commentRepository.findByPostdate(postdate);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByTextContaining(String text) {
        return commentRepository.findByTextContaining(text);
    }

    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, Comment commentDetails) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            // Update comment details
            comment.setPost(commentDetails.getPost());
            comment.setUser(commentDetails.getUser());
            comment.setText(commentDetails.getText());
            comment.setPostdate(commentDetails.getPostdate());

            return commentRepository.save(comment);
        }
        return null; // Return null if comment not found
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}