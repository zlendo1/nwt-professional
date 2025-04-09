package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.application.ApplicationDto;
import ba.unsa.etf.test_skills_service.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Kažemo MapStruct-u da koristi druge mappere za ugnježdene objekte
@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, TestMapper.class})
public interface ApplicationMapper {

  // MapStruct će automatski koristiti UserMapper.toUserSummaryDto za 'user'
  // i TestMapper.toTestSummaryDto za 'test'
  @Mapping(source = "user", target = "user") // Mapira User entitet u UserSummaryDto
  @Mapping(source = "test", target = "test") // Mapira Test entitet u TestSummaryDto
  ApplicationDto toApplicationDto(Application application);

  // Mapping from CreateApplicationRequest to Application is usually done in the service
  // because it involves fetching User and Test entities by ID first.
}
