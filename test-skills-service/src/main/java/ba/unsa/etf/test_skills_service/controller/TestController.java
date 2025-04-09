package ba.unsa.etf.test_skills_service.controller;

import ba.unsa.etf.test_skills_service.dto.test.CreateTestRequest;
import ba.unsa.etf.test_skills_service.dto.test.TestDto;
import ba.unsa.etf.test_skills_service.dto.test.UpdateTestRequest;
import ba.unsa.etf.test_skills_service.service.TestService;
import jakarta.validation.Valid; // Import za @Valid
import java.net.URI; // Za Location header
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Za Location header

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

  private final TestService testService;

  public TestController(TestService testService) {
    this.testService = testService;
  }

  @GetMapping
  public List<TestDto> getAllTests() { // Vraća List<TestDto>
    return testService.getAllTests();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TestDto> getTestById(
      @PathVariable Long id) { // Vraća ResponseEntity<TestDto>
    return testService
        .getTestById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found with id: " + id));
  }

  @PostMapping
  public ResponseEntity<TestDto> createTest(
      @Valid @RequestBody CreateTestRequest request) { // Prima CreateTestRequest
    try {
      TestDto createdTest = testService.createTest(request);
      // Kreiranje URI-ja za novokreirani resurs (dobra REST praksa)
      URI location =
          ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(createdTest.testId())
              .toUri();
      return ResponseEntity.created(location).body(createdTest); // Vraća 201 Created
    } catch (Exception e) {
      // Generalno rukovanje greškama, može se poboljšati sa @ControllerAdvice
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Could not create test: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<TestDto> updateTest(
      @PathVariable Long id,
      @Valid @RequestBody UpdateTestRequest request) { // Prima UpdateTestRequest
    return testService
        .updateTest(id, request)
        .map(ResponseEntity::ok) // Vraća 200 OK sa ažuriranim DTO
        .orElseThrow(
            () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found with id: " + id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
    try {
      if (testService.deleteTest(id)) {
        return ResponseEntity.noContent().build(); // Vraća 204 No Content
      } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found with id: " + id);
      }
    } catch (IllegalStateException ex) { // Hvata grešku ako ne može da se obriše
      throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting test", ex);
    }
  }
}
