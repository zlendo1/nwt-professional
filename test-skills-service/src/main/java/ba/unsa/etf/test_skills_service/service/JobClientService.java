package ba.unsa.etf.test_skills_service.service;

import ba.unsa.etf.test_skills_service.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JobClientService {

  @Autowired private WebClient.Builder webClientBuilder;

  public JobDTO fetchJobByUUID(String jobUUID, String token) {
    return webClientBuilder
        .build()
        .get()
        .uri("http://api-gateway/api/job/uuid/{uuid}", jobUUID)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(JobDTO.class)
        .block(); // koristi .block() samo ako nisi u reaktivnom konte
  }
}
