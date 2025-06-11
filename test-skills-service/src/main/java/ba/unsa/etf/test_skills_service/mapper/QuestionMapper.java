package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.*;
import ba.unsa.etf.test_skills_service.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {AnswerOptionMapper.class})
public interface QuestionMapper {
  QuestionDTOs.QuestionResponse toQuestionDto(Question question);

  @Mapping(target = "test", ignore = true)
  @Mapping(target = "applicantAnswers", ignore = true)
  Question toQuestion(QuestionDTOs.CreateQuestionRequest createQuestionRequest);

  void updateQuestionFromDto(
      QuestionDTOs.UpdateQuestionRequest updateQuestionRequest, @MappingTarget Question question);
}
