package ba.unsa.etf.content_service.repository;

import ba.unsa.etf.content_service.entity.Comment;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findByUser(User user);

  List<Comment> findByPostdate(LocalDate localDate);

  List<Comment> findByTextContaining(String text);
}
