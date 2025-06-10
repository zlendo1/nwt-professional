package ba.unsa.etf.test_skills_service.dto;

import lombok.Data;

public class AnswerOptionDTOs {

  @Data
  public static class AnswerOptionResponse {
    private Long id;
    private String optionText;
    private Boolean isCorrect;
  }

  @Data
  public static class CreateAnswerOptionRequest {
    private String optionText;
    private Boolean isCorrect;
  }

  @Data
  public static class UpdateAnswerOptionRequest {
    private String optionText;
    private Boolean isCorrect;
  }
}
