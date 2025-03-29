package ba.unsa.etf.job_service.controller;

import ba.unsa.etf.job_service.dto.JobDTO;
import ba.unsa.etf.job_service.dto.JobSearchDTO;
import ba.unsa.etf.job_service.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")  // Ruta za poslove
public class JobController {

    @Autowired
    private JobService jobService;

    // Kreiranje novog posla
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    // Dohvatanje posla prema ID-u
    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId) {
        JobDTO job = jobService.getJobById(jobId);
        return job != null ? ResponseEntity.ok(job) : ResponseEntity.notFound().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestBody JobSearchDTO jobSearchDTO) {
        // Call the service to search for jobs based on the DTO
        List<JobDTO> jobDTOList = jobService.searchJobs(jobSearchDTO);

        // Return the list of jobs as a response
        return ResponseEntity.ok(jobDTOList);
    }
}

