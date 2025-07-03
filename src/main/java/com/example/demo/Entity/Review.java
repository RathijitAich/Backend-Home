package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to MaintenanceWorker (by email)
    @ManyToOne
    @JoinColumn(name = "worker_email", referencedColumnName = "email")
    private MaintenanceWorker worker;

    // Foreign key to Homeowner (by email)
    @ManyToOne
    @JoinColumn(name = "homeowner_email", referencedColumnName = "email")
    private Homeowner homeowner;

    // Foreign key to Job (by issueTitle)
    @ManyToOne
    @JoinColumn(name = "job_issue_title", referencedColumnName = "issueTitle")
    private Job job;

    // Review content
    @Column(columnDefinition = "TEXT")
    private String reviewText;

    // Rating (1-5 stars)
    private Integer rating;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MaintenanceWorker getWorker() { return worker; }
    public void setWorker(MaintenanceWorker worker) { this.worker = worker; }

    public Homeowner getHomeowner() { return homeowner; }
    public void setHomeowner(Homeowner homeowner) { this.homeowner = homeowner; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}