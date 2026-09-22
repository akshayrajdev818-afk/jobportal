package com.jobconnect.controller;

import com.jobconnect.entity.Job;
import com.jobconnect.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	// CREATE JOB
	@PostMapping
	public ResponseEntity<Job> createJob(@RequestBody Job job) {
		Job savedJob = jobService.createJob(job);
		return ResponseEntity.ok(savedJob);
	}

	// GET ALL JOBS
	@GetMapping
	public ResponseEntity<List<Job>> getAllJobs() {
		return ResponseEntity.ok(jobService.getAllJobs());
	}

	@GetMapping("/search")
	public ResponseEntity<List<Job>> searchJobs(@RequestParam String keyword) {

		return ResponseEntity.ok(jobService.searchJobs(keyword));
	}

	@GetMapping("/location")
	public ResponseEntity<List<Job>> getJobsByLocation(@RequestParam String location) {

		return ResponseEntity.ok(jobService.getJobsByLocation(location));
	}

	@GetMapping("/type")
	public ResponseEntity<List<Job>> getJobsByEmploymentType(@RequestParam String employmentType) {

		return ResponseEntity.ok(jobService.getJobsByEmploymentType(employmentType));
	}

	// GET JOB BY ID
	@GetMapping("/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable Long id) {
		return jobService.getJobById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// UPDATE JOB
	@PutMapping("/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {

		return ResponseEntity.ok(jobService.updateJob(id, job));
	}

	// DELETE JOB
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {

		jobService.deleteJob(id);

		return ResponseEntity.noContent().build();
	}

}