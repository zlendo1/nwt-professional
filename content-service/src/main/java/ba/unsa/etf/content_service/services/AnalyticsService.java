package ba.unsa.etf.content_service.services;

import ba.unsa.etf.content_service.entity.Analytics;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Autowired
    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Transactional(readOnly = true)
    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Analytics> getAnalyticsById(Long id) {
        return analyticsRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Analytics> getAnalyticsByPost(Post post) {
        return analyticsRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Analytics> getAnalyticsByViewsCountGreaterThan(Integer viewsCount) {
        return analyticsRepository.findByViewsCountGreaterThan(viewsCount);
    }


    @Transactional(readOnly = true)
    public List<Analytics> getAnalyticsByCommentsCountGreaterThan(Integer commentsCount) {
        return analyticsRepository.findByCommentsCountGreaterThan(commentsCount);
    }

    @Transactional
    public Analytics createAnalytics(Analytics analytics) {
        return analyticsRepository.save(analytics);
    }

    @Transactional
    public Analytics updateAnalytics(Long id, Analytics analyticsDetails) {
        Optional<Analytics> existingAnalytics = analyticsRepository.findById(id);
        if (existingAnalytics.isPresent()) {
            Analytics analytics = existingAnalytics.get();
            // Update analytics details
            analytics.setPost(analyticsDetails.getPost());
            analytics.setViewsCount(analyticsDetails.getViewsCount());
            analytics.setLikesCount(analyticsDetails.getLikesCount());
            analytics.setCommentsCount(analyticsDetails.getCommentsCount());

            return analyticsRepository.save(analytics);
        }
        return null; // Return null if analytics not found
    }

    @Transactional
    public void deleteAnalytics(Long id) {
        analyticsRepository.deleteById(id);
    }
}