package com.jobconnect.service;

import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateProfileService(
            CandidateProfileRepository candidateProfileRepository) {

        this.candidateProfileRepository = candidateProfileRepository;
    }

    // Create profile
    public CandidateProfile createProfile(
            CandidateProfile profile) {

        return candidateProfileRepository.save(profile);
    }

    // Get all profiles
    public List<CandidateProfile> getAllProfiles() {

        return candidateProfileRepository.findAll();
    }

    // Get profile by ID
    public CandidateProfile getProfileById(Long id) {

        return candidateProfileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidate profile not found"));
    }
}