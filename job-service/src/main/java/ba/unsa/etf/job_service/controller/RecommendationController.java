package ba.unsa.etf.job_service.controller;

import ba.unsa.etf.job_service.dto.RecommendationDTO;
import ba.unsa.etf.job_service.service.RecommendationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

  @Autowired private RecommendationService recommendationService;

  @GetMapping("/jobs/{jobId}")
  public ResponseEntity<List<RecommendationDTO>> getRecommendations(@PathVariable Long jobId) {
    return ResponseEntity.ok(recommendationService.getCandidateRecommendations(jobId));
  }
}
