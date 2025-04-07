package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.JobDTO;
import ba.unsa.etf.job_service.dto.JobSearchDTO;
import ba.unsa.etf.job_service.model.Company;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.repository.CompanyRepository;
import ba.unsa.etf.job_service.repository.JobRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    // Pretraga kompanije po imenu
    Optional<Company> companyOpt = companyRepository.findByName(jobDTO.getCompanyName());

    if (companyOpt.isEmpty()) {
      // Ako kompanija nije pronađena, možeš da baciš exception ili da kreiraš novu kompaniju
      throw new RuntimeException("Company not found");
    }

    Company company = companyOpt.get();

    // Mapiramo JobDTO u Job entitet
    Job job = convertToEntity(jobDTO);

    // Popunjavanje Company objekta
    job.setCompany(company);

    // Sačuvaj posao u bazi
    job = jobRepository.save(job);

    // Vratimo JobDTO sa sačuvanim podacima
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

  public List<JobDTO> searchJobs(JobSearchDTO jobSearchDTO) {
    List<Job> jobs =
        jobRepository
            .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(
                jobSearchDTO.getJobTitle(),
                jobSearchDTO.getLocation(),
                jobSearchDTO.getCompanyName());

    return jobs.stream()
        .map(job -> modelMapper.map(job, JobDTO.class))
        .collect(Collectors.toList());
  }
}
