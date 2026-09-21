package com.jobconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String location;

    private Double salaryMin;

    private Double salaryMax;

    private Integer experienceRequired;

    private String employmentType;

    private String skills;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;

    // getters and setters
}