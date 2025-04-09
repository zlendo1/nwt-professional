package ba.unsa.etf.test_skills_service.controller;

import ba.unsa.etf.test_skills_service.dto.application.ApplicationDto;
import ba.unsa.etf.test_skills_service.dto.application.CreateApplicationRequest;
import ba.unsa.etf.test_skills_service.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid; // Import za @Valid
import java.math.BigDecimal;
import java.net.URI; // Za Location header
import java.util.List;
import java.util.Map; // Za primanje score-a (privremeno rešenje)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Za Location header

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @GetMapping
  public List<ApplicationDto> getAllApplications() { // Vraća List<ApplicationDto>
    return applicationService.getAllApplications();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApplicationDto> getApplicationById(
      @PathVariable Long id) { // Vraća ResponseEntity<ApplicationDto>
    return applicationService
        .getApplicationById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Application not found with id: " + id));
  }

  @PostMapping
  public ResponseEntity<ApplicationDto> createApplication(
      @Valid @RequestBody CreateApplicationRequest request) { // Prima CreateApplicationRequest
    try {
      ApplicationDto createdApplication = applicationService.createApplication(request);
      // Kreiranje URI-ja za novokreirani resurs
      URI location =
          ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(createdApplication.applicationId())
              .toUri();
      return ResponseEntity.created(location).body(createdApplication); // Vraća 201 Created
    } catch (EntityNotFoundException ex) {
      // Vratiti odgovarajući error response ako User/Test nije nađen
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error creating application", ex);
    }
  }

  @PostMapping("/{id}/start-test")
  public ResponseEntity<ApplicationDto> startTest(
      @PathVariable Long id) { // Vraća ResponseEntity<ApplicationDto>
    try {
      return applicationService
          .startTest(id)
          .map(ResponseEntity::ok)
          .orElseThrow(
              () ->
                  new ResponseStatusException(
                      HttpStatus.NOT_FOUND, "Application not found with id: " + id));
    } catch (IllegalStateException ex) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, ex.getMessage(), ex); // 409 Conflict ako test ne može da se startuje
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error starting test", ex);
    }
  }

  // Koristimo Map za score kao u prethodnom primeru, mada bi specifičan DTO bio bolji
  @PostMapping("/{id}/complete-test")
  public ResponseEntity<ApplicationDto> completeTest(
      @PathVariable Long id,
      @RequestBody Map<String, BigDecimal> payload) { // Vraća ResponseEntity<ApplicationDto>
    BigDecimal score = payload.get("score");
    if (score == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Score is required in the request body");
    }
    try {
      return applicationService
          .completeTest(id, score)
          .map(ResponseEntity::ok)
          .orElseThrow(
              () ->
                  new ResponseStatusException(
                      HttpStatus.NOT_FOUND, "Application not found with id: " + id));
    } catch (IllegalStateException ex) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, ex.getMessage(), ex); // 409 Conflict ako test ne može da se završi
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error completing test", ex);
    }
  }

  // TODO: Implementirati PUT / DELETE endpoints po potrebi, koristeći DTOs
}
