
package com.jobconnect.service;

import com.jobconnect.entity.Job;
import com.jobconnect.entity.User;
import com.jobconnect.repository.JobRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Create a job and assign it to the authenticated recruiter.
    public Job createJob(Job job, User recruiter) {
        job.setRecruiter(recruiter);
        job.setActive(true);

        return jobRepository.save(job);
    }

    // Get all jobs.
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get jobs belonging to a specific recruiter.
    @Transactional(readOnly = true)
    public List<Job> getMyJobs(Long recruiterId) {
        return jobRepository.findByRecruiter_Id(recruiterId);
    }

    // Get a job by ID.
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Update an existing job without replacing its recruiter.
    public Job updateJob(Long id, Job updatedJob) {

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalaryMin(updatedJob.getSalaryMin());
        existingJob.setSalaryMax(updatedJob.getSalaryMax());
        existingJob.setExperienceRequired(
                updatedJob.getExperienceRequired());
        existingJob.setEmploymentType(
                updatedJob.getEmploymentType());
        existingJob.setSkills(updatedJob.getSkills());
        existingJob.setActive(updatedJob.isActive());

        return jobRepository.save(existingJob);
    }

    // Delete a job.
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }

        jobRepository.deleteById(id);
    }

    // Search jobs by title.
    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Find jobs by location.
    public List<Job> getJobsByLocation(String location) {
        return jobRepository.findByLocationIgnoreCase(location);
    }

    // Find jobs by employment type.
    public List<Job> getJobsByEmploymentType(String employmentType) {
        return jobRepository.findByEmploymentTypeIgnoreCase(
                employmentType);
    }
}

