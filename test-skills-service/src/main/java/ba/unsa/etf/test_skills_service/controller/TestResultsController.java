package ba.unsa.etf.test_skills_service.controller;

import ba.unsa.etf.test_skills_service.dto.TestResultsDTOs;
import ba.unsa.etf.test_skills_service.service.TestResultsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-results")
@RequiredArgsConstructor
public class TestResultsController {
  private final TestResultsService testResultService;

  @GetMapping
  public ResponseEntity<List<TestResultsDTOs.TestResultResponse>> getAllResults() {
    return ResponseEntity.ok(testResultService.getAllResults());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TestResultsDTOs.TestResultResponse> getResultById(@PathVariable Long id) {
    return ResponseEntity.of(testResultService.getResultById(id));
  }

  @GetMapping("/test/{testId}")
  public ResponseEntity<List<TestResultsDTOs.TestResultResponse>> getResultsByTestId(
      @PathVariable Long testId) {
    return ResponseEntity.ok(testResultService.getResultsByTestId(testId));
  }

  @GetMapping("/applicant/{applicationId}")
  public ResponseEntity<List<TestResultsDTOs.TestResultResponse>> getResultsByApplicationId(
      @PathVariable Long applicationId) {
    return ResponseEntity.ok(testResultService.getResultsByApplicationId(applicationId));
  }

  @PostMapping
  public ResponseEntity<TestResultsDTOs.TestResultResponse> createTestResult(
      @RequestBody TestResultsDTOs.CreateTestResultRequest request) {
    return ResponseEntity.ok(testResultService.createTestResult(request));
  }
}
