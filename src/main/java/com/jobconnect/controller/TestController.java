package com.jobconnect.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/test")
	public String test(Authentication authentication) {

		return "Hello " + authentication.getName() + ", JWT authentication is working!";
	}

	@GetMapping("/api/candidate/test")
	public String candidateTest() {
		return "Candidate access granted";
	}

	@GetMapping("/api/recruiter/test")
	public String recruiterTest() {
		return "Recruiter access granted";
	}

	@GetMapping("/api/admin/test")
	public String adminTest() {
		return "Admin access granted";
	}
}