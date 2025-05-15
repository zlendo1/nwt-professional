package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.AnalyticsDto;
import ba.unsa.etf.content_service.entity.Analytics;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

  @Mapping(source = "post.postId", target = "postId") // Mapiranje postId iz entiteta
  AnalyticsDto toDTO(Analytics analytics);

  @Mapping(target = "post", ignore = true) // Ignorišemo post prilikom kreiranja analitike
  Analytics toEntity(AnalyticsDto analyticsDTO);

  // Dodaj ovu metodu za mapiranje liste
  List<AnalyticsDto> toDTO(List<Analytics> analyticsList); // Mapiranje liste objekata
}
