package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.model.AnswerOfApplicant;
import ba.unsa.etf.test_skills_service.model.ApplicantTestResult;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {ApplicantAnswerMapper.class})
public interface TestResultsMapper {
  @Mapping(target = "answers", source = "answers", qualifiedByName = "mapAnswers")
  TestResultsDTOs.TestResultResponse toTestResultDto(ApplicantTestResult testResult);

  @Mapping(target = "test", ignore = true)
  @Mapping(target = "completionDate", ignore = true)
  ApplicantTestResult toTestResult(TestResultsDTOs.CreateTestResultRequest createTestResultRequest);

  @Named("mapAnswers")
  default List<ApplicantAnswerDTOs.ApplicantAnswerResponse> mapAnswers(
      List<AnswerOfApplicant> answers) {
    return answers.stream()
        .map(
            answer -> {
              ApplicantAnswerDTOs.ApplicantAnswerResponse response =
                  new ApplicantAnswerDTOs.ApplicantAnswerResponse();
              response.setId(answer.getId());
              response.setQuestionId(answer.getQuestion().getId());
              response.setChosenAnswerOptionId(answer.getChosenAnswerOption().getId());
              response.setAnsweredAt(answer.getAnsweredAt());
              return response;
            })
        .toList();
  }
}
