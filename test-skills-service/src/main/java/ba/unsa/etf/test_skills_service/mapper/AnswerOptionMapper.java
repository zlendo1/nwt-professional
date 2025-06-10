package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.model.AnswerOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {
  AnswerOptionDTOs.AnswerOptionResponse toAnswerOptionDto(AnswerOption answerOption);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "applicantAnswers", ignore = true)
  AnswerOption toAnswerOption(AnswerOptionDTOs.CreateAnswerOptionRequest createAnswerOptionRequest);

  void updateAnswerOptionFromDto(
      AnswerOptionDTOs.UpdateAnswerOptionRequest updateAnswerOptionRequest,
      @MappingTarget AnswerOption answerOption);
}
