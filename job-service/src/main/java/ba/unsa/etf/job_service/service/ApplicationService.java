package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.ApplicationDTO;
import ba.unsa.etf.job_service.model.Application;
import ba.unsa.etf.job_service.model.Job;
import ba.unsa.etf.job_service.model.User;
import ba.unsa.etf.job_service.repository.ApplicationRepository;
import ba.unsa.etf.job_service.repository.JobRepository;
import ba.unsa.etf.job_service.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  @Autowired private ModelMapper modelMapper;
  @Autowired private ApplicationRepository applicationRepository;
  @Autowired private JobRepository jobRepository;
  @Autowired private UserRepository userRepository;

  public ApplicationDTO convertToDTO(Application application) {
    ApplicationDTO applicationDTO = new ApplicationDTO();
    applicationDTO.setJobId(application.getJob().getId());
    applicationDTO.setUserId(application.getUser().getId());
    applicationDTO.setJobTitle(
        application.getJob().getTitle()); // Assuming you have getTitle() in Job
    applicationDTO.setApplicationDate(application.getApplicationDate());
    applicationDTO.setStatus(application.getStatus());
    return applicationDTO;
  }

  public Application convertToEntity(ApplicationDTO applicationDTO) {
    Optional<Job> job = jobRepository.findById(applicationDTO.getJobId());
    Optional<User> user = userRepository.findById(applicationDTO.getUserId());

    if (job.isPresent() && user.isPresent()) {
      Application application = new Application();
      application.setJob(job.get());
      application.setUser(user.get());
      application.setApplicationDate(applicationDTO.getApplicationDate());
      application.setStatus(applicationDTO.getStatus());
      return application;
    } else {
      throw new IllegalArgumentException("Job or User not found");
    }
  }

  public ApplicationDTO applyForJob(ApplicationDTO applicationDTO) {
    // Fetch the Job by jobId
    Job job =
        jobRepository
            .findById(applicationDTO.getJobId())
            .orElseThrow(() -> new RuntimeException("Job not found"));

    // Fetch the User by userId
    User user =
        userRepository
            .findById(applicationDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Create a new Application entity
    Application application = new Application();
    application.setJob(job);
    application.setUser(user);
    application.setApplicationDate(
        applicationDTO.getApplicationDate() != null
            ? applicationDTO.getApplicationDate()
            : new Date());
    application.setStatus(applicationDTO.getStatus());

    // Save the application
    Application savedApplication = applicationRepository.save(application);

    return modelMapper.map(savedApplication, ApplicationDTO.class);
  }

  public List<ApplicationDTO> getUserApplications(Long userId) {
    List<Application> applications = applicationRepository.findByUser_Id(userId);
    return applications.stream()
        .map(application -> modelMapper.map(application, ApplicationDTO.class))
        .collect(Collectors.toList());
  }

  public List<ApplicationDTO> getApplicationsForCompany(Long companyId) {
    // Fetch all applications for jobs in the company
    List<Application> applications = applicationRepository.findByJob_Company_Id(companyId);

    // Convert to DTO (assuming you have a method to convert Application to ApplicationDTO)
    return applications.stream().map(this::convertToDTO).collect(Collectors.toList());
  }
}
