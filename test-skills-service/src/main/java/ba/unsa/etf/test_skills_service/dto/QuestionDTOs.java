package ba.unsa.etf.test_skills_service.dto;

import java.util.List;
import lombok.Data;

public class QuestionDTOs {

  @Data
  public static class QuestionResponse {
    private Long id;
    private Long testId;
    private String questionText;
    private Integer points;
    private List<AnswerOptionDTOs.AnswerOptionResponse> answerOptions;
  }

  @Data
  public static class CreateQuestionRequest {
    private Long testId;
    private String questionText;
    private Integer points;
    private List<AnswerOptionDTOs.CreateAnswerOptionRequest> answerOptions;
  }

  @Data
  public static class UpdateQuestionRequest {
    private String questionText;
    private Integer points;
  }
}
