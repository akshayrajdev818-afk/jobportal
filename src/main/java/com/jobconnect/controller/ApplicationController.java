package com.jobconnect.controller;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.ApplicationStatus;
import com.jobconnect.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    // APPLY FOR JOB
    @PostMapping
    public ResponseEntity<Application> applyForJob(
            @RequestBody Application application) {

        return ResponseEntity.ok(
                applicationService.applyForJob(application)
        );
    }

    // GET ALL APPLICATIONS
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {

        return ResponseEntity.ok(
                applicationService.getAllApplications()
        );
    }

    // GET APPLICATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                applicationService.getApplicationById(id)
        );
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {

        return ResponseEntity.ok(
                applicationService.updateStatus(id, status)
        );
    }

    // DELETE APPLICATION
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}