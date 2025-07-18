package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "budget_alerts")
public class BudgetAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String userEmail;
    
    @Column(nullable = false)
    private Double groceryBudget = 0.0;
    
    @Column(nullable = false)
    private Double grocerySpent = 0.0;
    
    @Column(nullable = false)
    private Double electricityBudget = 0.0;
    
    @Column(nullable = false)
    private Double electricityEstimate = 0.0;
    
    // Constructors
    public BudgetAlert() {}
    
    public BudgetAlert(String userEmail, Double groceryBudget, Double grocerySpent, 
                      Double electricityBudget, Double electricityEstimate) {
        this.userEmail = userEmail;
        this.groceryBudget = groceryBudget;
        this.grocerySpent = grocerySpent;
        this.electricityBudget = electricityBudget;
        this.electricityEstimate = electricityEstimate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public Double getGroceryBudget() {
        return groceryBudget;
    }
    
    public void setGroceryBudget(Double groceryBudget) {
        this.groceryBudget = groceryBudget;
    }
    
    public Double getGrocerySpent() {
        return grocerySpent;
    }
    
    public void setGrocerySpent(Double grocerySpent) {
        this.grocerySpent = grocerySpent;
    }
    
    public Double getElectricityBudget() {
        return electricityBudget;
    }
    
    public void setElectricityBudget(Double electricityBudget) {
        this.electricityBudget = electricityBudget;
    }
    
    public Double getElectricityEstimate() {
        return electricityEstimate;
    }
    
    public void setElectricityEstimate(Double electricityEstimate) {
        this.electricityEstimate = electricityEstimate;
    }
}