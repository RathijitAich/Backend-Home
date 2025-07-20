package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_reminders")
public class MaintenanceReminder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String device;
    
    @Column(nullable = false)
    private String task;
    
    @Column(nullable = false)
    private String frequency;
    
    @Column
    private LocalDate lastDone;
    
    @Column(nullable = false)
    private LocalDate nextDue;
    
    @Column(nullable = false)
    private String userEmail;
    
    // Constructors
    public MaintenanceReminder() {}
    
    public MaintenanceReminder(String device, String task, String frequency, 
                             LocalDate lastDone, LocalDate nextDue, String userEmail) {
        this.device = device;
        this.task = task;
        this.frequency = frequency;
        this.lastDone = lastDone;
        this.nextDue = nextDue;
        this.userEmail = userEmail;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDevice() {
        return device;
    }
    
    public void setDevice(String device) {
        this.device = device;
    }
    
    public String getTask() {
        return task;
    }
    
    public void setTask(String task) {
        this.task = task;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public LocalDate getLastDone() {
        return lastDone;
    }
    
    public void setLastDone(LocalDate lastDone) {
        this.lastDone = lastDone;
    }
    
    public LocalDate getNextDue() {
        return nextDue;
    }
    
    public void setNextDue(LocalDate nextDue) {
        this.nextDue = nextDue;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}