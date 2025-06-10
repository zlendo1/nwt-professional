package ba.unsa.etf.test_skills_service.controller;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.service.TestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {
  private final TestService testService;

  @GetMapping
  public ResponseEntity<List<TestDTOs.TestResponse>> getAllTests() {
    return ResponseEntity.ok(testService.getAllTests());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TestDTOs.TestWithQuestionsResponse> getTestById(@PathVariable Long id) {
    return ResponseEntity.of(testService.getTestById(id));
  }

  @PostMapping
  public ResponseEntity<TestDTOs.TestResponse> createTest(
      @RequestBody TestDTOs.CreateTestRequest request) {
    return ResponseEntity.ok(testService.createTest(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TestDTOs.TestResponse> updateTest(
      @PathVariable Long id, @RequestBody TestDTOs.UpdateTestRequest request) {
    return ResponseEntity.of(testService.updateTest(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
    return testService.deleteTest(id)
        ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }

  @PostMapping("/questions")
  public ResponseEntity<QuestionDTOs.QuestionResponse> addQuestionToTest(
      @RequestBody QuestionDTOs.CreateQuestionRequest request) {
    return ResponseEntity.ok(testService.addQuestionToTest(request));
  }
}
