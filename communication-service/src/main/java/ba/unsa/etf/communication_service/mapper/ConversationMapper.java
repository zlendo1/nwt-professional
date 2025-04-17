package ba.unsa.etf.communication_service.mapper;

import ba.unsa.etf.communication_service.dto.ConversationDTO;
import ba.unsa.etf.communication_service.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
  ConversationDTO toConversationDTO(Conversation conversation);
}
