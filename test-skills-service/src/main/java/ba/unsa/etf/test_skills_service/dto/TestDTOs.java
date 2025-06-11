package ba.unsa.etf.test_skills_service.dto;

import java.util.List;
import lombok.Data;

public class TestDTOs {

  @Data
  public static class TestResponse {
    private Long id;
    private String name;
    private String type;
    private Integer timeLimitMinutes;
    private String description;
  }

  @Data
  public static class TestWithQuestionsResponse {
    private Long id;
    private String name;
    private String type;
    private Integer timeLimitMinutes;
    private String description;
    private List<QuestionDTOs.QuestionResponse> questions;
  }

  @Data
  public static class CreateTestRequest {
    private String name;
    private String type;
    private Integer timeLimitMinutes;
    private String description;
  }

  @Data
  public static class UpdateTestRequest {
    private String name;
    private String type;
    private Integer timeLimitMinutes;
    private String description;
  }
}
