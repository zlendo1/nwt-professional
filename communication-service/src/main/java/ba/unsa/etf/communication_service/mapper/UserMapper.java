package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.UserDTO;
import ba.unsa.etf.communication_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDTO toUserDTO(User user);
}
