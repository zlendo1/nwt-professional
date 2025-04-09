package ba.unsa.etf.test_skills_service.service;

import ba.unsa.etf.test_skills_service.dto.test.CreateTestRequest;
import ba.unsa.etf.test_skills_service.dto.test.TestDto;
import ba.unsa.etf.test_skills_service.dto.test.UpdateTestRequest;
import ba.unsa.etf.test_skills_service.mapper.TestMapper;
import ba.unsa.etf.test_skills_service.model.Test;
import ba.unsa.etf.test_skills_service.repository.TestRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

  private final TestRepository testRepository;
  private final TestMapper testMapper; // Inject Mapper

  // private final ApplicationRepository applicationRepository; // Inject ako je potrebna provera

  public TestService(
      TestRepository testRepository,
      TestMapper testMapper /*, ApplicationRepository applicationRepository */) {
    this.testRepository = testRepository;
    this.testMapper = testMapper;
    // this.applicationRepository = applicationRepository;
  }

  @Transactional(readOnly = true)
  public List<TestDto> getAllTests() {
    return testRepository.findAll().stream()
        .map(testMapper::toTestDto) // Mapiraj svaki Test u TestDto
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Optional<TestDto> getTestById(Long id) {
    return testRepository
        .findById(id)
        .map(testMapper::toTestDto); // Mapiraj pronađeni Test u TestDto
  }

  @Transactional
  public TestDto createTest(CreateTestRequest request) {
    // 1. Mapiraj Request DTO u Entity
    Test test = testMapper.toTest(request);
    // 2. Dodatna logika ako je potrebna (npr. inicijalizacija nečega)
    // 3. Sačuvaj entitet
    Test savedTest = testRepository.save(test);
    // 4. Mapiraj sačuvani entitet nazad u DTO za odgovor
    return testMapper.toTestDto(savedTest);
  }

  @Transactional
  public Optional<TestDto> updateTest(Long id, UpdateTestRequest request) {
    return testRepository
        .findById(id)
        .map(
            existingTest -> {
              // Ažuriraj polja iz DTO-a
              existingTest.setTitle(request.title());
              existingTest.setDescription(request.description());
              existingTest.setDurationMinutes(request.durationMinutes());
              // Čuvanje ažuriranog entiteta
              Test updatedTest = testRepository.save(existingTest);
              // Mapiraj u DTO za odgovor
              return testMapper.toTestDto(updatedTest);
            });
  }

  @Transactional
  public boolean deleteTest(Long id) {
    if (!testRepository.existsById(id)) {
      return false; // Test ne postoji
    }
    // Opciona provera: Da li postoje prijave koje koriste ovaj test?
    // long applicationCount = applicationRepository.countByTestTestId(id); // Potrebna metoda u App
    // repo
    // if (applicationCount > 0) {
    //     throw new IllegalStateException("Cannot delete Test with ID " + id + " as it is linked to
    // existing applications.");
    // }

    testRepository.deleteById(id);
    return true;
  }
}
