package ba.unsa.etf.test_skills_service.service;

import static org.assertj.core.api.Assertions.assertThat; // AssertJ za fluentne asertacije
import static org.junit.jupiter.api.Assertions.*;

import ba.unsa.etf.test_skills_service.dto.application.ApplicationDto;
import ba.unsa.etf.test_skills_service.dto.application.CreateApplicationRequest;
import ba.unsa.etf.test_skills_service.model.Application;
import ba.unsa.etf.test_skills_service.model.User;
import ba.unsa.etf.test_skills_service.repository.ApplicationRepository;
import ba.unsa.etf.test_skills_service.repository.TestRepository;
import ba.unsa.etf.test_skills_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // JUnit 5 Test annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional; // Važno za testove

@SpringBootTest // Učitava kompletan Spring context
@Transactional // Svaki test metod se izvršava u transakciji koja se rollback-uje na kraju
class ApplicationServiceIntegrationTest {

  @Autowired private ApplicationService applicationService;

  // Repozitorijumi su i dalje potrebni za setup i proveru stanja baze
  @Autowired private ApplicationRepository applicationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private TestRepository testRepository;

  private User testUser;
  private ba.unsa.etf.test_skills_service.model.Test testTest;

  @BeforeEach
  void setUp() {
    // Čišćenje prije svakog testa da se izbegnu konflikti
    applicationRepository.deleteAll();
    userRepository.deleteAll();
    testRepository.deleteAll();

    // Kreiramo potrebne preduslove (User, Test)
    testUser =
        new User(
            null,
            "testuser-integration",
            "test-int@example.com",
            "hashedpassword",
            "TestInt",
            "UserInt",
            null);
    testUser = userRepository.saveAndFlush(testUser); // Flush da bude sigurno dostupan

    testTest =
        new ba.unsa.etf.test_skills_service.model.Test(
            null, "Integration Java Test", "Test Java for integration", 30, null, null);
    testTest = testRepository.saveAndFlush(testTest); // Flush da bude sigurno dostupan
  }

  @Test
  void shouldCreateApplicationSuccessfully() {
    // Arrange
    CreateApplicationRequest request =
        new CreateApplicationRequest(
            testUser.getUserId(), testTest.getTestId(), 456L); // Koristimo DTO za zahtev

    // Act
    ApplicationDto createdApplicationDto =
        applicationService.createApplication(request); // Servis sada vraća DTO

    // Assert using DTO fields
    assertNotNull(createdApplicationDto);
    assertNotNull(createdApplicationDto.applicationId());
    assertThat(createdApplicationDto.user().userId()).isEqualTo(testUser.getUserId());
    assertThat(createdApplicationDto.user().username()).isEqualTo(testUser.getUsername());
    assertThat(createdApplicationDto.test().testId()).isEqualTo(testTest.getTestId());
    assertThat(createdApplicationDto.test().title()).isEqualTo(testTest.getTitle());
    assertThat(createdApplicationDto.status()).isEqualTo("Pending");
    assertThat(createdApplicationDto.testStatus()).isEqualTo("Not Started");
    assertThat(createdApplicationDto.jobPostingId()).isEqualTo(456L);
    assertThat(createdApplicationDto.applicationDate())
        .isEqualTo(LocalDate.now()); // Provera default datuma
    assertThat(createdApplicationDto.createdAt())
        .isNotNull(); // Provera da li je timestamp postavljen

    // Assert persistence (Optional but good)
    Optional<Application> foundInDb =
        applicationRepository.findById(createdApplicationDto.applicationId());
    assertThat(foundInDb).isPresent();
    assertThat(foundInDb.get().getUser().getUserId()).isEqualTo(testUser.getUserId());
    assertThat(foundInDb.get().getTest().getTestId()).isEqualTo(testTest.getTestId());
  }

  @Test
  void shouldStartTestSuccessfully() {
    // Arrange: Kreiraj aplikaciju prvo koristeći servis
    CreateApplicationRequest createRequest =
        new CreateApplicationRequest(testUser.getUserId(), testTest.getTestId(), null);
    ApplicationDto applicationDto = applicationService.createApplication(createRequest);
    Long applicationId = applicationDto.applicationId();

    assertThat(applicationDto.testStatus()).isEqualTo("Not Started");
    assertThat(applicationDto.testStartedAt()).isNull();

    // Act
    Optional<ApplicationDto> updatedDtoOpt = applicationService.startTest(applicationId);

    // Assert
    assertThat(updatedDtoOpt).isPresent();
    ApplicationDto updatedDto = updatedDtoOpt.get();
    assertThat(updatedDto.applicationId()).isEqualTo(applicationId);
    assertThat(updatedDto.testStatus()).isEqualTo("In Progress");
    assertThat(updatedDto.testStartedAt()).isNotNull(); // Vreme starta treba da bude postavljeno
    assertThat(updatedDto.testCompletedAt()).isNull();
    assertThat(updatedDto.testScore()).isNull();
  }

  @Test
  void shouldCompleteTestSuccessfully() {
    // Arrange: Kreiraj i startuj aplikaciju
    CreateApplicationRequest createRequest =
        new CreateApplicationRequest(testUser.getUserId(), testTest.getTestId(), null);
    ApplicationDto applicationDto = applicationService.createApplication(createRequest);
    Long applicationId = applicationDto.applicationId();
    // Startuj test i uzmi ažurirani DTO (ignorišemo Optional za jednostavnost u testu)
    applicationService.startTest(applicationId).orElseThrow();

    BigDecimal expectedScore = new BigDecimal("92.75");

    // Act
    Optional<ApplicationDto> completedDtoOpt =
        applicationService.completeTest(applicationId, expectedScore);

    // Assert
    assertThat(completedDtoOpt).isPresent();
    ApplicationDto completedDto = completedDtoOpt.get();
    assertThat(completedDto.applicationId()).isEqualTo(applicationId);
    assertThat(completedDto.testStatus()).isEqualTo("Completed");
    assertThat(completedDto.testStartedAt()).isNotNull();
    assertThat(completedDto.testCompletedAt())
        .isNotNull(); // Vreme završetka treba da bude postavljeno
    assertThat(completedDto.testScore()).isNotNull();
    // Koristimo compareTo za BigDecimal poređenje
    assertThat(completedDto.testScore()).isEqualByComparingTo(expectedScore);
  }

  @Test
  void shouldFailToStartAlreadyStartedOrCompletedTest() {
    // Arrange: Kreiraj, startuj i završi aplikaciju
    CreateApplicationRequest createRequest =
        new CreateApplicationRequest(testUser.getUserId(), testTest.getTestId(), null);
    ApplicationDto applicationDto = applicationService.createApplication(createRequest);
    Long appId = applicationDto.applicationId();
    applicationService.startTest(appId).orElseThrow(); // Startuj
    applicationService.completeTest(appId, BigDecimal.TEN).orElseThrow(); // Završi

    // Act & Assert for completed test
    assertThrows(
        IllegalStateException.class,
        () -> {
          applicationService.startTest(appId);
        },
        "Should throw exception when starting a completed test");

    // Arrange: Kreiraj i startuj drugu
    ApplicationDto startedAppDto =
        applicationService.createApplication(createRequest); // Kreiraj novu
    Long startedAppId = startedAppDto.applicationId();
    applicationService.startTest(startedAppId).orElseThrow(); // Samo startuj

    // Act & Assert for already started test
    assertThrows(
        IllegalStateException.class,
        () -> {
          applicationService.startTest(startedAppId);
        },
        "Should throw exception when starting an already in-progress test");
  }

  @Test
  void shouldThrowExceptionWhenCreatingApplicationWithNonExistentUser() {
    // Arrange
    Long nonExistentUserId = 99999L;
    CreateApplicationRequest request =
        new CreateApplicationRequest(nonExistentUserId, testTest.getTestId(), null);

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          applicationService.createApplication(request);
        },
        "Should throw EntityNotFoundException for non-existent user");
  }

  @Test
  void shouldThrowExceptionWhenCreatingApplicationWithNonExistentTest() {
    // Arrange
    Long nonExistentTestId = 88888L;
    CreateApplicationRequest request =
        new CreateApplicationRequest(testUser.getUserId(), nonExistentTestId, null);

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          applicationService.createApplication(request);
        },
        "Should throw EntityNotFoundException for non-existent test");
  }

  @Test
  void getApplicationByIdShouldReturnCorrectDto() {
    // Arrange
    CreateApplicationRequest request =
        new CreateApplicationRequest(testUser.getUserId(), testTest.getTestId(), 777L);
    ApplicationDto createdDto = applicationService.createApplication(request);

    // Act
    Optional<ApplicationDto> foundDtoOpt =
        applicationService.getApplicationById(createdDto.applicationId());

    // Assert
    assertThat(foundDtoOpt).isPresent();
    ApplicationDto foundDto = foundDtoOpt.get();
    assertThat(foundDto.applicationId()).isEqualTo(createdDto.applicationId());
    assertThat(foundDto.user().userId()).isEqualTo(testUser.getUserId());
    assertThat(foundDto.test().testId()).isEqualTo(testTest.getTestId());
    assertThat(foundDto.jobPostingId()).isEqualTo(777L);
  }

  @Test
  void getApplicationByIdShouldReturnEmptyForNonExistentId() {
    // Act
    Optional<ApplicationDto> foundDtoOpt = applicationService.getApplicationById(99999L);

    // Assert
    assertThat(foundDtoOpt).isNotPresent();
  }

  // TODO: Dodati testove za update i delete ako/kada se implementiraju u servisu
}
