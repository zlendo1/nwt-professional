package ba.unsa.etf.test_skills_service.service;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.mapper.*;
import ba.unsa.etf.test_skills_service.model.*;
import ba.unsa.etf.test_skills_service.repository.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestResultsService {
  private final TestResultsRepository testResultRepository;
  private final TestRepository testRepository;
  private final QuestionRepository questionRepository;
  private final AnswerOptionRepository answerOptionRepository;
  private final ApplicantAnswerRepository applicantAnswerRepository;
  private final TestResultsMapper testResultMapper;
  private final ApplicantAnswerMapper applicantAnswerMapper;

  @Transactional(readOnly = true)
  public List<TestResultsDTOs.TestResultResponse> getAllResults() {
    return testResultRepository.findAll().stream().map(testResultMapper::toTestResultDto).toList();
  }

  @Transactional(readOnly = true)
  public List<TestResultsDTOs.TestResultResponse> getResultsByTestId(Long testId) {
    return testResultRepository.findByTestId(testId).stream()
        .map(testResultMapper::toTestResultDto)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<TestResultsDTOs.TestResultResponse> getResultsByApplicationId(Long applicationId) {
    return testResultRepository.findByApplicationId(applicationId).stream()
        .map(testResultMapper::toTestResultDto)
        .toList();
  }

  @Transactional
  public TestResultsDTOs.TestResultResponse createTestResult(
      TestResultsDTOs.CreateTestResultRequest request) {
    Test test =
        testRepository
            .findById(request.getTestId())
            .orElseThrow(() -> new RuntimeException("Test not found"));

    ApplicantTestResult testResult = testResultMapper.toTestResult(request);
    testResult.setTest(test);
    testResult.setCompletionDate(LocalDateTime.now());

    // Calculate score
    double score = calculateScore(request.getAnswers());
    testResult.setScore(score);
    testResult.setPassed(score >= 70.0); // Assuming passing score is 70%

    ApplicantTestResult savedResult = testResultRepository.save(testResult);

    // Save answers
    request
        .getAnswers()
        .forEach(
            answerRequest -> {
              AnswerOfApplicant answer = applicantAnswerMapper.toApplicantAnswer(answerRequest);
              answer.setApplicantTestResult(savedResult);
              answer.setQuestion(
                  questionRepository
                      .findById(answerRequest.getQuestionId())
                      .orElseThrow(() -> new RuntimeException("Question not found")));
              answer.setChosenAnswerOption(
                  answerOptionRepository
                      .findById(answerRequest.getChosenAnswerOptionId())
                      .orElseThrow(() -> new RuntimeException("Answer option not found")));
              answer.setAnsweredAt(LocalDateTime.now());
              applicantAnswerRepository.save(answer);
            });

    return testResultMapper.toTestResultDto(savedResult);
  }

  private double calculateScore(List<ApplicantAnswerDTOs.CreateApplicantAnswerRequest> answers) {
    long correctAnswers =
        answers.stream()
            .filter(
                answer -> {
                  AnswerOption option =
                      answerOptionRepository
                          .findById(answer.getChosenAnswerOptionId())
                          .orElseThrow(() -> new RuntimeException("Answer option not found"));
                  return option.getIsCorrect();
                })
            .count();

    return answers.isEmpty() ? 0 : (double) correctAnswers / answers.size() * 100;
  }

  @Transactional(readOnly = true)
  public Optional<TestResultsDTOs.TestResultResponse> getResultById(Long id) {
    return testResultRepository
        .findById(id)
        .map(
            testResult -> {
              TestResultsDTOs.TestResultResponse dto = testResultMapper.toTestResultDto(testResult);
              dto.setAnswers(
                  applicantAnswerRepository.findByApplicantTestResultId(id).stream()
                      .map(
                          answer -> {
                            ApplicantAnswerDTOs.ApplicantAnswerResponse response =
                                new ApplicantAnswerDTOs.ApplicantAnswerResponse();
                            response.setId(answer.getId());
                            response.setQuestionId(answer.getQuestion().getId());
                            response.setChosenAnswerOptionId(
                                answer.getChosenAnswerOption().getId());
                            response.setAnsweredAt(answer.getAnsweredAt());
                            return response;
                          })
                      .toList());
              return dto;
            });
  }
}
