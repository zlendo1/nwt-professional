package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.ApplicantAnswerDTOs;
import ba.unsa.etf.test_skills_service.model.AnswerOfApplicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicantAnswerMapper {
  @Mapping(target = "applicantTestResult", ignore = true)
  @Mapping(target = "question", ignore = true)
  @Mapping(target = "chosenAnswerOption", ignore = true)
  @Mapping(target = "answeredAt", ignore = true)
  AnswerOfApplicant toApplicantAnswer(
      ApplicantAnswerDTOs.CreateApplicantAnswerRequest createRequest);
}
