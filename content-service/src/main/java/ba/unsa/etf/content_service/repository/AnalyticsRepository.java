package ba.unsa.etf.content_service.repository;

import ba.unsa.etf.content_service.entity.Analytics;
import ba.unsa.etf.content_service.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
  Optional<Analytics> findByPost(Post post);

  List<Analytics> findByViewsCountGreaterThan(Integer viewsCount);

  List<Analytics> findByCommentsCountGreaterThan(Integer commentsCount);
}
