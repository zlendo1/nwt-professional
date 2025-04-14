package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.RecommendationDTO;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.Recommendation;
import ba.unsa.etf.job_service.model.User;
import ba.unsa.etf.job_service.repository.JobRepository;
import ba.unsa.etf.job_service.repository.RecommendationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private JobRepository jobRepository;

    public RecommendationDTO convertToDTO(Recommendation recommendation) {
        return modelMapper.map(recommendation, RecommendationDTO.class);
    }

    public Recommendation convertToEntity(RecommendationDTO recommendationDTO) {
        return modelMapper.map(recommendationDTO, Recommendation.class);
    }

    public List<RecommendationDTO> getCandidateRecommendations(Long jobId) {
        // Fetch the job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Get recommendations for this job
        List<Recommendation> recommendations = recommendationRepository.findByJob(job);

        // Convert to DTOs
        return recommendations.stream()
                .map(recommendation -> {
                    User user = recommendation.getUser();
                    return new RecommendationDTO(
                            job.getTitle(),
                            user.getFirstName() + " " + user.getLastName() // Assuming User has firstName & lastName
                    );
                })
                .collect(Collectors.toList());
    }
}

