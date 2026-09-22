package com.jobconnect.controller;

import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.service.CandidateProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(
            CandidateProfileService candidateProfileService) {

        this.candidateProfileService = candidateProfileService;
    }

    // CREATE PROFILE
    @PostMapping
    public ResponseEntity<CandidateProfile> createProfile(
            @RequestBody CandidateProfile profile) {

        return ResponseEntity.ok(
                candidateProfileService.createProfile(profile)
        );
    }

    // GET ALL PROFILES
    @GetMapping
    public ResponseEntity<List<CandidateProfile>> getAllProfiles() {

        return ResponseEntity.ok(
                candidateProfileService.getAllProfiles()
        );
    }

    // GET PROFILE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CandidateProfile> getProfileById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                candidateProfileService.getProfileById(id)
        );
    }
}