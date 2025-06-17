package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.message.CreateMessageDTO;
import ba.unsa.etf.communication_service.dto.message.MessageDTO;
import ba.unsa.etf.communication_service.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user.email", target = "email")
  @Mapping(source = "conversation.id", target = "conversationId")
  MessageDTO toDTO(Message message);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "conversation", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  Message fromCreateDTO(CreateMessageDTO createMessageDTO);
}
