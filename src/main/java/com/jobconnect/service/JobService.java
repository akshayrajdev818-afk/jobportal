package com.jobconnect.service;

import com.jobconnect.entity.Job;
import com.jobconnect.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Create Job
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    // Get All Jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get Job By ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Update Job
    public Job updateJob(Long id, Job updatedJob) {

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalaryMin(updatedJob.getSalaryMin());
        existingJob.setSalaryMax(updatedJob.getSalaryMax());
        existingJob.setExperienceRequired(updatedJob.getExperienceRequired());
        existingJob.setEmploymentType(updatedJob.getEmploymentType());
        existingJob.setSkills(updatedJob.getSkills());
        existingJob.setActive(updatedJob.isActive());

        return jobRepository.save(existingJob);
    }

    // Delete Job
    public void deleteJob(Long id) {

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }

        jobRepository.deleteById(id);
    }
    public List<Job> searchJobs(String keyword) {

        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Job> getJobsByLocation(String location) {

        return jobRepository.findByLocationIgnoreCase(location);
    }

    public List<Job> getJobsByEmploymentType(String employmentType) {

        return jobRepository.findByEmploymentTypeIgnoreCase(employmentType);
    }
}