package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.user.CreateUserDTO;
import ba.unsa.etf.communication_service.dto.user.UserDTO;
import ba.unsa.etf.communication_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDTO toDTO(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "conversations", ignore = true)
  User fromCreateDTO(CreateUserDTO createUserDTO);
}
