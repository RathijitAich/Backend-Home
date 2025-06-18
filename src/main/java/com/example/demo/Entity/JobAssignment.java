
package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_assignments")
public class JobAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to Job using issueTitle
    @ManyToOne
    @JoinColumn(name = "job_issue_title", referencedColumnName = "issueTitle")
    private Job job;

    // Foreign key to MaintenanceWorker using email
    @ManyToOne
    @JoinColumn(name = "worker_email", referencedColumnName = "email")
    private MaintenanceWorker worker;

    // Foreign key to Homeowner using email
    @ManyToOne
    @JoinColumn(name = "homeowner_email", referencedColumnName = "email")
    private Homeowner homeowner;

    // Normal column for job completion status
    @Column(name = "Application_status")
    private String applicationStatus;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public MaintenanceWorker getWorker() { return worker; }
    public void setWorker(MaintenanceWorker worker) { this.worker = worker; }

    public Homeowner getHomeowner() { return homeowner; }
    public void setHomeowner(Homeowner homeowner) { this.homeowner = homeowner; }

    public String getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }
}