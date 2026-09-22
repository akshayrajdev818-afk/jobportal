package com.jobconnect.service;

import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(
            RecruiterProfileRepository recruiterProfileRepository) {

        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    // Create recruiter profile
    public RecruiterProfile createProfile(
            RecruiterProfile profile) {

        return recruiterProfileRepository.save(profile);
    }

    // Get all recruiter profiles
    public List<RecruiterProfile> getAllProfiles() {

        return recruiterProfileRepository.findAll();
    }

    // Get recruiter profile by ID
    public RecruiterProfile getProfileById(Long id) {

        return recruiterProfileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found"));
    }
}