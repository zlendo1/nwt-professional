package ba.unsa.etf.test_skills_service.service;

import ba.unsa.etf.test_skills_service.dto.application.ApplicationDto;
import ba.unsa.etf.test_skills_service.dto.application.CreateApplicationRequest;
import ba.unsa.etf.test_skills_service.mapper.ApplicationMapper;
import ba.unsa.etf.test_skills_service.model.Application;
import ba.unsa.etf.test_skills_service.model.Test;
import ba.unsa.etf.test_skills_service.model.User;
import ba.unsa.etf.test_skills_service.repository.ApplicationRepository;
import ba.unsa.etf.test_skills_service.repository.TestRepository;
import ba.unsa.etf.test_skills_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final UserRepository userRepository;
  private final TestRepository testRepository;
  private final ApplicationMapper applicationMapper; // Inject Mapper

  public ApplicationService(
      ApplicationRepository applicationRepository,
      UserRepository userRepository,
      TestRepository testRepository,
      ApplicationMapper applicationMapper) {
    this.applicationRepository = applicationRepository;
    this.userRepository = userRepository;
    this.testRepository = testRepository;
    this.applicationMapper = applicationMapper;
  }

  @Transactional(readOnly = true)
  public List<ApplicationDto> getAllApplications() {
    return applicationRepository.findAll().stream()
        .map(applicationMapper::toApplicationDto) // Mapiraj u DTO
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<ApplicationDto> getApplicationById(Long id) {
    return applicationRepository
        .findById(id)
        .map(applicationMapper::toApplicationDto); // Mapiraj u DTO
  }

  @Transactional
  public ApplicationDto createApplication(CreateApplicationRequest request) {
    // 1. Pronađi User i Test entitete na osnovu ID-eva iz DTO-a
    User user =
        userRepository
            .findById(request.userId())
            .orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + request.userId()));
    Test test =
        testRepository
            .findById(request.testId())
            .orElseThrow(
                () -> new EntityNotFoundException("Test not found with id: " + request.testId()));

    // 2. Kreiraj novi Application entitet
    Application newApplication = new Application();
    newApplication.setUser(user);
    newApplication.setTest(test);
    newApplication.setJobPostingId(request.jobPostingId());
    // Statusi i datumi imaju default vrednosti u entitetu ili se mogu postaviti ovde

    // 3. Sačuvaj entitet
    Application savedApplication = applicationRepository.save(newApplication);

    // 4. Mapiraj sačuvani entitet u DTO za odgovor
    return applicationMapper.toApplicationDto(savedApplication);
  }

  @Transactional
  public Optional<ApplicationDto> startTest(Long applicationId) {
    return applicationRepository
        .findById(applicationId)
        .map(
            app -> {
              if (!"Not Started".equals(app.getTestStatus())) {
                throw new IllegalStateException(
                    "Test cannot be started. Current status: " + app.getTestStatus());
              }
              app.setTestStatus("In Progress");
              app.setTestStartedAt(LocalDateTime.now());
              Application updatedApp = applicationRepository.save(app);
              return applicationMapper.toApplicationDto(updatedApp); // Vrati DTO
            });
  }

  @Transactional
  public Optional<ApplicationDto> completeTest(
      Long applicationId, BigDecimal score) { // Prima score direktno za sada
    return applicationRepository
        .findById(applicationId)
        .map(
            app -> {
              if (!"In Progress".equals(app.getTestStatus())) {
                throw new IllegalStateException(
                    "Test cannot be completed. Current status: " + app.getTestStatus());
              }
              app.setTestStatus("Completed");
              app.setTestCompletedAt(LocalDateTime.now());
              app.setTestScore(score);
              // Možda promeniti i generalni status aplikacije?
              // app.setStatus("Test Completed");
              Application updatedApp = applicationRepository.save(app);
              return applicationMapper.toApplicationDto(updatedApp); // Vrati DTO
            });
  }

  // TODO: Implement updateApplication (verovatno za status) i deleteApplication metode koje vraćaju
  // DTOs
}
