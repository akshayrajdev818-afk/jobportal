package com.jobconnect.controller;

import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.service.RecruiterProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(
            RecruiterProfileService recruiterProfileService) {

        this.recruiterProfileService = recruiterProfileService;
    }

    // CREATE PROFILE
    @PostMapping
    public ResponseEntity<RecruiterProfile> createProfile(
            @RequestBody RecruiterProfile profile) {

        return ResponseEntity.ok(
                recruiterProfileService.createProfile(profile)
        );
    }

    // GET ALL PROFILES
    @GetMapping
    public ResponseEntity<List<RecruiterProfile>> getAllProfiles() {

        return ResponseEntity.ok(
                recruiterProfileService.getAllProfiles()
        );
    }

    // GET PROFILE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RecruiterProfile> getProfileById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recruiterProfileService.getProfileById(id)
        );
    }
}