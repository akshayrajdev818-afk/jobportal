
package com.jobconnect.controller;

import com.jobconnect.entity.Job;
import com.jobconnect.entity.User;
import com.jobconnect.service.JobService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Create a job for the authenticated recruiter.
    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestBody Job job,
            @AuthenticationPrincipal User recruiter) {

        if (recruiter == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        Job savedJob = jobService.createJob(job, recruiter);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedJob);
    }

    // Get all jobs.
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // Get jobs belonging to the authenticated recruiter.
    @GetMapping("/my-jobs")
    public ResponseEntity<List<Job>> getMyJobs(
            @AuthenticationPrincipal User recruiter) {

        if (recruiter == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        List<Job> jobs = jobService.getMyJobs(recruiter.getId());

        return ResponseEntity.ok(jobs);
    }

    // Search jobs by title.
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                jobService.searchJobs(keyword));
    }

    // Find jobs by location.
    @GetMapping("/location")
    public ResponseEntity<List<Job>> getJobsByLocation(
            @RequestParam String location) {

        return ResponseEntity.ok(
                jobService.getJobsByLocation(location));
    }

    // Find jobs by employment type.
    @GetMapping("/type")
    public ResponseEntity<List<Job>> getJobsByEmploymentType(
            @RequestParam String employmentType) {

        return ResponseEntity.ok(
                jobService.getJobsByEmploymentType(employmentType));
    }

    // Get a job by ID.
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a job.
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        return ResponseEntity.ok(
                jobService.updateJob(id, job));
    }

    // Delete a job.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseEntity.noContent().build();
    }
}

