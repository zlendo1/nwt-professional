package ba.unsa.etf.content_service.controller;

import ba.unsa.etf.content_service.dto.AnalyticsDto;
import ba.unsa.etf.content_service.entity.Analytics;
import ba.unsa.etf.content_service.entity.Post;
import ba.unsa.etf.content_service.mapper.AnalyticsMapper;
import ba.unsa.etf.content_service.repository.PostRepository;
import ba.unsa.etf.content_service.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final AnalyticsMapper analyticsMapper;
    private final PostRepository postRepository;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService, AnalyticsMapper analyticsMapper, PostRepository postRepository) {
        this.analyticsService = analyticsService;
        this.analyticsMapper = analyticsMapper;
        this.postRepository = postRepository;
    }

    // GET all analytics
    @GetMapping
    public ResponseEntity<List<AnalyticsDto>> getAllAnalytics() {
        List<Analytics> analyticsList = analyticsService.getAllAnalytics();
        List<AnalyticsDto> analyticsDtos = analyticsMapper.toDTO(analyticsList);
        return new ResponseEntity<>(analyticsDtos, HttpStatus.OK);
    }

    // GET analytics by ID
    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsDto> getAnalyticsById(@PathVariable("id") Long id) {
        Optional<Analytics> analytics = analyticsService.getAnalyticsById(id);
        return analytics.map(value -> new ResponseEntity<>(analyticsMapper.toDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST create new analytics
    @PostMapping
    public ResponseEntity<AnalyticsDto> createAnalytics(@RequestBody AnalyticsDto analyticsDto) {
        Analytics analytics = analyticsMapper.toEntity(analyticsDto);

        Post post = postRepository.findById(analyticsDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + analyticsDto.getPostId()));

        analytics.setPost(post);

        Analytics createdAnalytics = analyticsService.createAnalytics(analytics);
        AnalyticsDto createdAnalyticsDto = analyticsMapper.toDTO(createdAnalytics);
        return new ResponseEntity<>(createdAnalyticsDto, HttpStatus.CREATED);
    }

    // DELETE analytics by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalytics(@PathVariable("id") Long id) {
        analyticsService.deleteAnalytics(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
