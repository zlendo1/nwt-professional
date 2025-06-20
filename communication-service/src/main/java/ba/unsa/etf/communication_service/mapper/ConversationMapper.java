package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.conversation.ConversationDTO;
import ba.unsa.etf.communication_service.dto.conversation.CreateConversationDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
  @Mapping(source = "user1.id", target = "user1_id")
  @Mapping(source = "user2.id", target = "user2_id")
  @Mapping(source = "user1.email", target = "email1")
  @Mapping(source = "user2.email", target = "email2")
  ConversationDTO toDTO(Conversation conversation);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user1", ignore = true)
  @Mapping(target = "user2", ignore = true)
  @Mapping(target = "messages", ignore = true)
  Conversation fromCreateDTO(CreateConversationDTO createConversationDTO);
}
