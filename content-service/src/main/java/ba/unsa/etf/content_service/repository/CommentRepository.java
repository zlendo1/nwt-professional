package ba.unsa.etf.content_service.repository;

import ba.unsa.etf.content_service.entity.Comment;
import ba.unsa.etf.content_service.entity.User;
import ba.unsa.etf.content_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
    List<Comment> findByPostdate(LocalDate localDate);
    List<Comment> findByTextContaining(String text);
}