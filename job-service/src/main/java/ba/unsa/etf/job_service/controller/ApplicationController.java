package ba.unsa.etf.job_service.controller;

import ba.unsa.etf.job_service.dto.ApplicationDTO;
import ba.unsa.etf.job_service.service.ApplicationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

  @Autowired private ApplicationService applicationService;

  @PostMapping
  public ResponseEntity<ApplicationDTO> applyForJob(@RequestBody ApplicationDTO applicationDTO) {
    return ResponseEntity.ok(applicationService.applyForJob(applicationDTO));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<ApplicationDTO>> getUserApplications(@PathVariable Long userId) {
    return ResponseEntity.ok(applicationService.getUserApplications(userId));
  }

  @GetMapping("/company/{companyId}")
  public ResponseEntity<List<ApplicationDTO>> getApplicationsForCompany(
      @PathVariable Long companyId) {
    List<ApplicationDTO> applications = applicationService.getApplicationsForCompany(companyId);
    return ResponseEntity.ok(applications);
  }
}
