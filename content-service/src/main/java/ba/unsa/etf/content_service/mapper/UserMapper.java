package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.UserDto;
import ba.unsa.etf.content_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
