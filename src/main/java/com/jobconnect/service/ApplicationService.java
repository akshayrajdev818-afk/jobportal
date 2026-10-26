
package com.jobconnect.service;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.ApplicationStatus;
import com.jobconnect.entity.User;
import com.jobconnect.exception.DuplicateApplicationException;
import com.jobconnect.repository.ApplicationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                applicationRepository.existsByJobIdAndCandidateId(
                        jobId, candidateId);

        if (alreadyApplied) {
            throw new DuplicateApplicationException(
                    "Candidate has already applied for this job");
        }

        application.setApplicationDate(LocalDate.now());
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    // Existing method: get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Existing method: get application by ID
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));
    }

    // Recruiter: get applications for their own jobs
    @Transactional(readOnly = true)
    public List<Application> getApplicationsForRecruiter(Long recruiterId) {
        return applicationRepository.findByJob_Recruiter_Id(recruiterId);
    }

    // Recruiter: update status only for their own job applications
    @Transactional
    public Application updateStatus(
            Long applicationId,
            ApplicationStatus status,
            User recruiter) {

        Application application = getApplicationById(applicationId);

        if (application.getJob() == null
                || application.getJob().getRecruiter() == null
                || !application.getJob().getRecruiter().getId()
                        .equals(recruiter.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot update applications for another recruiter's job");
        }

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    // Existing method retained for compatibility
    public Application updateStatus(
            Long id,
            ApplicationStatus status) {

        Application application = getApplicationById(id);
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    // Existing delete method retained
    public void deleteApplication(Long id) {

        if (!applicationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Application not found");
        }

        applicationRepository.deleteById(id);
    }
    
   
 // Candidate: get applications submitted by this candidate
 @Transactional(readOnly = true)
 public List<Application> getApplicationsForCandidate(Long candidateId) {
     return applicationRepository.findByCandidate_Id(candidateId);
 }

}

