package com.jobconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobconnect.entity.Job;

public interface JobRepository
        extends JpaRepository<Job, Long> {
}