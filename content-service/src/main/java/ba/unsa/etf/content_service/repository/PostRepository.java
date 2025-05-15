package ba.unsa.etf.content_service.repository;

import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.entity.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByUser(User user); // Find posts by User entity

  List<Post> findByPostDate(LocalDate postDate);

  List<Post> findByStatus(String status);

  List<Post> findByTextContaining(String text);

  List<Post> findByUserAndStatus(User user, String status);
}
