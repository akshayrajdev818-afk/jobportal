package com.jobconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobconnect.entity.RecruiterProfile;

public interface RecruiterProfileRepository
        extends JpaRepository<RecruiterProfile, Long> {
}