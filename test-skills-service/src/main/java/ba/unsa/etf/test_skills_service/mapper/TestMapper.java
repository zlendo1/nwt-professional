package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.model.Question;
import ba.unsa.etf.test_skills_service.model.Test;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {QuestionMapper.class})
public interface TestMapper {
  TestDTOs.TestResponse toTestDto(Test test);

  @Mapping(target = "questions", source = "questions", qualifiedByName = "mapQuestions")
  TestDTOs.TestWithQuestionsResponse toTestWithQuestionsDto(Test test);

  Test toTest(TestDTOs.CreateTestRequest createTestRequest);

  @Named("mapQuestions")
  default List<QuestionDTOs.QuestionResponse> mapQuestions(List<Question> questions) {
    return questions.stream()
        .map(
            question -> {
              QuestionDTOs.QuestionResponse response = new QuestionDTOs.QuestionResponse();
              response.setId(question.getId());
              response.setTestId(question.getTest().getId());
              response.setQuestionText(question.getQuestionText());
              response.setPoints(question.getPoints());
              return response;
            })
        .toList();
  }
}
