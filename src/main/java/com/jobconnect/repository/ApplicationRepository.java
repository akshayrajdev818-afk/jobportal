
package com.jobconnect.repository;

import com.jobconnect.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

    List<Application> findByJob_Recruiter_Id(Long recruiterId);

    // Get applications belonging to a specific candidate
    List<Application> findByCandidate_Id(Long candidateId);
    
    
}

