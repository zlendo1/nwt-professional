package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.test.CreateTestRequest;
import ba.unsa.etf.test_skills_service.dto.test.TestDto;
import ba.unsa.etf.test_skills_service.dto.test.TestSummaryDto;
import ba.unsa.etf.test_skills_service.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestMapper {

  TestDto toTestDto(Test test);

  TestSummaryDto toTestSummaryDto(Test test);

  @Mapping(target = "testId", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "questions", ignore = true) // Ne mapiramo pitanja direktno iz Create requesta
  Test toTest(CreateTestRequest request);
}
