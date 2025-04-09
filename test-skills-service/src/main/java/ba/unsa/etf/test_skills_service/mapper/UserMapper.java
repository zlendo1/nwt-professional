package ba.unsa.etf.test_skills_service.mapper;

import ba.unsa.etf.test_skills_service.dto.user.CreateUserRequest;
import ba.unsa.etf.test_skills_service.dto.user.UserDto;
import ba.unsa.etf.test_skills_service.dto.user.UserSummaryDto;
import ba.unsa.etf.test_skills_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Generates a Spring bean
public interface UserMapper {

  // Entity -> DTOs
  UserDto toUserDto(User user);

  UserSummaryDto toUserSummaryDto(User user);

  // Request DTO -> Entity
  // Ignorišemo 'password' iz DTO-a jer će ga servis heširati
  @Mapping(target = "userId", ignore = true) // Ne mapiramo ID pri kreiranju
  @Mapping(target = "passwordHash", ignore = true) // Servis će postaviti heširanu lozinku
  @Mapping(target = "createdAt", ignore = true) // Baza ili @CreationTimestamp će postaviti ovo
  User toUser(CreateUserRequest request);

  // Note: Update mapping often needs @MappingTarget or manual logic in service
}
