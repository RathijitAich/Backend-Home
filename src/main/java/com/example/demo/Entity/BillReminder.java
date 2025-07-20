package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill_reminders")
public class BillReminder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private LocalDate dueDate;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private String frequency;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String userEmail;
    
    @Column(nullable = false)
    private LocalDateTime created;
    
    @PrePersist
    protected void onCreate() {
        if (this.created == null) {
            this.created = LocalDateTime.now();
        }
    }
    
    // Constructors
    public BillReminder() {}
    
    public BillReminder(String type, String title, LocalDate dueDate, Double amount, 
                       String frequency, String description, String userEmail) {
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.amount = amount;
        this.frequency = frequency;
        this.description = description;
        this.userEmail = userEmail;
        this.created = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }
    
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}