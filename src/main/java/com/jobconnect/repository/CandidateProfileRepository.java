package com.jobconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobconnect.entity.CandidateProfile;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {
}