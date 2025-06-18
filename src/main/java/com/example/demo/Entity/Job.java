package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceType;
    private String roomLocation;
    
    @Column(unique = true, nullable = false)
    private String issueTitle;


    private String issueDescription;
    private String priorityLevel;
    private String preferredDate;
    private String preferredTime;
    private String accessInstructions;

    private String jobStatus;


    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    // Foreign key to Homeowner (by email)
    @ManyToOne
    @JoinColumn(name = "homeowner_email", referencedColumnName = "email")
    private Homeowner homeowner;

    // Foreign key to MaintenanceWorker (by email, can be null if not assigned)
    @ManyToOne
    @JoinColumn(name = "worker_email", referencedColumnName = "email")
    private MaintenanceWorker worker;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getRoomLocation() { return roomLocation; }
    public void setRoomLocation(String roomLocation) { this.roomLocation = roomLocation; }

    public String getIssueTitle() { return issueTitle; }
    public void setIssueTitle(String issueTitle) { this.issueTitle = issueTitle; }

    public String getIssueDescription() { return issueDescription; }
    public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }

    public String getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }

    public String getPreferredDate() { return preferredDate; }
    public void setPreferredDate(String preferredDate) { this.preferredDate = preferredDate; }

    public String getPreferredTime() { return preferredTime; }
    public void setPreferredTime(String preferredTime) { this.preferredTime = preferredTime; }

    public String getAccessInstructions() { return accessInstructions; }
    public void setAccessInstructions(String accessInstructions) { this.accessInstructions = accessInstructions; }

    public Homeowner getHomeowner() { return homeowner; }
    public void setHomeowner(Homeowner homeowner) { this.homeowner = homeowner; }

    public MaintenanceWorker getWorker() { return worker; }
    public void setWorker(MaintenanceWorker worker) { this.worker = worker; }
}