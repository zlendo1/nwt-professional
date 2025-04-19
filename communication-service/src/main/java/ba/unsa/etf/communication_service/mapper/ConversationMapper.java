package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
  ConversationDTO toConversationDTO(Conversation conversation);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "users", ignore = true)
  @Mapping(target = "messages", ignore = true)
  Conversation fromCreateConversationDTO(CreateConversationDTO createConversationDTO);
}
