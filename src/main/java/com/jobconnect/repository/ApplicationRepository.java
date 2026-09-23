package com.jobconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobconnect.entity.Application;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {
	
	  boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);
}