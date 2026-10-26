
package com.jobconnect.controller;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.ApplicationStatus;
import com.jobconnect.entity.User;
import com.jobconnect.service.ApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

	private final ApplicationService applicationService;

	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	// Apply for a job
	@PostMapping
	public ResponseEntity<Application> applyForJob(@RequestBody Application application) {

		return ResponseEntity.ok(applicationService.applyForJob(application));
	}

	// Recruiter: view applications for their own jobs
	@GetMapping("/recruiter")
	public ResponseEntity<List<Application>> getRecruiterApplications(@AuthenticationPrincipal User recruiter) {

		return ResponseEntity.ok(applicationService.getApplicationsForRecruiter(recruiter.getId()));
	}

	// Existing endpoint retained; restrict its access before production
	@GetMapping
	public ResponseEntity<List<Application>> getAllApplications() {
		return ResponseEntity.ok(applicationService.getAllApplications());
	}

	// Existing endpoint retained; restrict its access before production
	@GetMapping("/{id}")
	public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {

		return ResponseEntity.ok(applicationService.getApplicationById(id));
	}

	// Recruiter: update status only for their own job applications
	@PutMapping("/{id}/status")
	public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status,
			@AuthenticationPrincipal User recruiter) {

		return ResponseEntity.ok(applicationService.updateStatus(id, status, recruiter));
	}

	// Existing delete endpoint retained
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {

		applicationService.deleteApplication(id);

		return ResponseEntity.noContent().build();
	}

	// Candidate: view applications submitted by the logged-in candidate
	@GetMapping("/my-applications")
	public ResponseEntity<List<Application>> getMyApplications(@AuthenticationPrincipal User candidate) {

		return ResponseEntity.ok(applicationService.getApplicationsForCandidate(candidate.getId()));
	}

}
