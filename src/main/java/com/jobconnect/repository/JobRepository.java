package com.jobconnect.repository;

import com.jobconnect.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findByTitleContainingIgnoreCase(String keyword);

	List<Job> findByLocationIgnoreCase(String location);

	List<Job> findByEmploymentTypeIgnoreCase(String employmentType);
}