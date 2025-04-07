package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.RecommendationDTO;
import ba.unsa.etf.job_service.model.Recommendation;
import ba.unsa.etf.job_service.repository.RecommendationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

  @Autowired private ModelMapper modelMapper;
  @Autowired private RecommendationRepository recommendationRepository;

  public RecommendationDTO convertToDTO(Recommendation recommendation) {
    return modelMapper.map(recommendation, RecommendationDTO.class);
  }

  public Recommendation convertToEntity(RecommendationDTO recommendationDTO) {
    return modelMapper.map(recommendationDTO, Recommendation.class);
  }
}
