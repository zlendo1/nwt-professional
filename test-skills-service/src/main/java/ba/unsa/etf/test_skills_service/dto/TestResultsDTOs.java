package ba.unsa.etf.test_skills_service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

public class TestResultsDTOs {

  @Data
  public static class TestResultResponse {
    private Long id;
    private Long applicationId;
    private Long testId;
    private Double score;
    private Boolean passed;
    private LocalDateTime completionDate;
    private List<ApplicantAnswerDTOs.ApplicantAnswerResponse> answers;
  }

  @Data
  public static class CreateTestResultRequest {
    private Long applicationId;
    private Long testId;
    private List<ApplicantAnswerDTOs.CreateApplicantAnswerRequest> answers;
  }
}
