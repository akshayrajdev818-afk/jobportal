package com.jobconnect.service;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.ApplicationStatus;
import com.jobconnect.exception.DuplicateApplicationException;
import com.jobconnect.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // Apply for a job
    public Application applyForJob(Application application) {

        Long jobId = application.getJob().getId();
        Long candidateId = application.getCandidate().getId();

        boolean alreadyApplied =
                applicationRepository
                        .existsByJobIdAndCandidateId(jobId, candidateId);

        if (alreadyApplied) {
            throw new DuplicateApplicationException(
                    "Candidate has already applied for this job");
        }

        application.setApplicationDate(LocalDate.now());
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get application by ID
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));
    }

    // Update application status
    public Application updateStatus(
            Long id,
            ApplicationStatus status) {

        Application application = getApplicationById(id);

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    // Delete application
    public void deleteApplication(Long id) {

        if (!applicationRepository.existsById(id)) {
            throw new RuntimeException("Application not found");
        }

        applicationRepository.deleteById(id);
    }
}