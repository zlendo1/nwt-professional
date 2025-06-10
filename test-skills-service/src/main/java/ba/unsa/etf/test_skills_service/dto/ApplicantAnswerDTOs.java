package ba.unsa.etf.test_skills_service.dto;

import java.time.LocalDateTime;
import lombok.Data;

public class ApplicantAnswerDTOs {

  @Data
  public static class ApplicantAnswerResponse {
    private Long id;
    private Long questionId;
    private Long chosenAnswerOptionId;
    private LocalDateTime answeredAt;
  }

  @Data
  public static class CreateApplicantAnswerRequest {
    private Long questionId;
    private Long chosenAnswerOptionId;
  }
}
