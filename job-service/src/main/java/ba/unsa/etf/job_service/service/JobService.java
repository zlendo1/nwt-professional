package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.JobDTO;
import ba.unsa.etf.job_service.model.Company;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.repository.CompanyRepository;
import ba.unsa.etf.job_service.repository.JobRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

  @Autowired private ModelMapper modelMapper;
  @Autowired private JobRepository jobRepository;
  @Autowired private CompanyRepository companyRepository;

  public JobDTO convertToDTO(Job job) {
    return modelMapper.map(job, JobDTO.class);
  }

  public Job convertToEntity(JobDTO jobDTO) {
    return modelMapper.map(jobDTO, Job.class);
  }

  public JobDTO createJob(JobDTO jobDTO) {
    Optional<Company> companyOpt = companyRepository.findByName(jobDTO.getCompanyName());

    if (companyOpt.isEmpty()) {
      throw new RuntimeException("Company not found");
    }

    Company company = companyOpt.get();
    Job job = convertToEntity(jobDTO);
    job.setCompany(company);
    job = jobRepository.save(job);
    return convertToDTO(job);
  }

  public JobDTO getJobById(Long jobId) {
    // Find job by jobId
    Optional<Job> jobOpt = jobRepository.findById(jobId);

    // If job not found, throw an exception
    if (jobOpt.isEmpty()) {
      throw new RuntimeException("Job with ID " + jobId + " not found");
    }

    // Convert Job entity to JobDTO
    return modelMapper.map(jobOpt.get(), JobDTO.class);
  }

  public Page<JobDTO> searchJobs(String title, String location, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Job> jobs;

    if (title != null && location != null) {
      jobs = jobRepository.findByTitleContainingAndLocationContaining(title, location, pageable);
    } else if (title != null) {
      jobs = jobRepository.findByTitleContaining(title, pageable);
    } else if (location != null) {
      jobs = jobRepository.findByLocationContaining(location, pageable);
    } else {
      jobs = jobRepository.findAll(pageable);
    }

    return jobs.map(job -> modelMapper.map(job, JobDTO.class));
  }

  public Job applyPatchToJob(JsonPatch patch, Job targetJob) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode patched = patch.apply(objectMapper.convertValue(targetJob, JsonNode.class));
      return objectMapper.treeToValue(patched, Job.class);
    } catch (JsonPatchException | JsonProcessingException e) {
      throw new RuntimeException("Failed to apply patch to Job", e);
    }
  }

  public JobDTO getJobById(String jobId) {
    // Find job by jobId
    Optional<Job> jobOpt = jobRepository.findByJobUUID(jobId);

    // If job not found, throw an exception
    if (jobOpt.isEmpty()) {
      throw new RuntimeException("Job with ID " + jobId + " not found");
    }

    // Convert Job entity to JobDTO
    return modelMapper.map(jobOpt.get(), JobDTO.class);
  }
}
