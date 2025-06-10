package ba.unsa.etf.test_skills_service.service;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.mapper.*;
import ba.unsa.etf.test_skills_service.model.*;
import ba.unsa.etf.test_skills_service.repository.*;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {
  private final TestRepository testRepository;
  private final QuestionRepository questionRepository;
  private final AnswerOptionRepository answerOptionRepository;
  private final TestMapper testMapper;
  private final QuestionMapper questionMapper;
  private final AnswerOptionMapper answerOptionMapper;

  @Transactional(readOnly = true)
  public List<TestDTOs.TestResponse> getAllTests() {
    return testRepository.findAll().stream().map(testMapper::toTestDto).toList();
  }

  @Transactional(readOnly = true)
  public Optional<TestDTOs.TestWithQuestionsResponse> getTestById(Long id) {
    return testRepository
        .findById(id)
        .map(
            test -> {
              TestDTOs.TestWithQuestionsResponse dto = testMapper.toTestWithQuestionsDto(test);
              dto.setQuestions(
                  questionRepository.findByTestId(id).stream()
                      .map(questionMapper::toQuestionDto)
                      .toList());
              return dto;
            });
  }

  @Transactional
  public TestDTOs.TestResponse createTest(TestDTOs.CreateTestRequest request) {
    Test test = testMapper.toTest(request);
    Test savedTest = testRepository.save(test);
    return testMapper.toTestDto(savedTest);
  }

  @Transactional
  public Optional<TestDTOs.TestResponse> updateTest(Long id, TestDTOs.UpdateTestRequest request) {
    return testRepository
        .findById(id)
        .map(
            test -> {
              if (request.getName() != null) test.setName(request.getName());
              if (request.getType() != null) test.setType(request.getType());
              if (request.getTimeLimitMinutes() != null)
                test.setTimeLimitMinutes(request.getTimeLimitMinutes());
              if (request.getDescription() != null) test.setDescription(request.getDescription());
              return testMapper.toTestDto(testRepository.save(test));
            });
  }

  @Transactional
  public boolean deleteTest(Long id) {
    if (testRepository.existsById(id)) {
      testRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public QuestionDTOs.QuestionResponse addQuestionToTest(
      QuestionDTOs.CreateQuestionRequest request) {
    return testRepository
        .findById(request.getTestId())
        .map(
            test -> {
              Question question = questionMapper.toQuestion(request);
              question.setTest(test);
              Question savedQuestion = questionRepository.save(question);

              request
                  .getAnswerOptions()
                  .forEach(
                      answerOptionRequest -> {
                        AnswerOption answerOption =
                            answerOptionMapper.toAnswerOption(answerOptionRequest);
                        answerOption.setQuestion(savedQuestion);
                        answerOptionRepository.save(answerOption);
                      });

              return questionMapper.toQuestionDto(savedQuestion);
            })
        .orElseThrow(() -> new RuntimeException("Test not found"));
  }
}
