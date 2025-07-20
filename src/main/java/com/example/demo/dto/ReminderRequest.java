package com.example.demo.dto;

import java.time.LocalDate;

public class ReminderRequest {
    private String userEmail;
    private BillReminderData reminder;
    private MaintenanceReminderData maintenanceReminder;
    private BudgetAlertData budgetAlerts;
    
    // Inner classes for data structures
    public static class BillReminderData {
        private String type;
        private String title;
        private LocalDate dueDate;
        private Double amount;
        private String frequency;
        private String description;
        
        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public LocalDate getDueDate() { return dueDate; }
        public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
        
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class MaintenanceReminderData {
        private String device;
        private String task;
        private String frequency;
        private LocalDate lastDone;
        private LocalDate nextDue;
        
        // Getters and Setters
        public String getDevice() { return device; }
        public void setDevice(String device) { this.device = device; }
        
        public String getTask() { return task; }
        public void setTask(String task) { this.task = task; }
        
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        
        public LocalDate getLastDone() { return lastDone; }
        public void setLastDone(LocalDate lastDone) { this.lastDone = lastDone; }
        
        public LocalDate getNextDue() { return nextDue; }
        public void setNextDue(LocalDate nextDue) { this.nextDue = nextDue; }
    }
    
    public static class BudgetAlertData {
        private Double groceryBudget;
        private Double grocerySpent;
        private Double electricityBudget;
        private Double electricityEstimate;
        
        // Getters and Setters
        public Double getGroceryBudget() { return groceryBudget; }
        public void setGroceryBudget(Double groceryBudget) { this.groceryBudget = groceryBudget; }
        
        public Double getGrocerySpent() { return grocerySpent; }
        public void setGrocerySpent(Double grocerySpent) { this.grocerySpent = grocerySpent; }
        
        public Double getElectricityBudget() { return electricityBudget; }
        public void setElectricityBudget(Double electricityBudget) { this.electricityBudget = electricityBudget; }
        
        public Double getElectricityEstimate() { return electricityEstimate; }
        public void setElectricityEstimate(Double electricityEstimate) { this.electricityEstimate = electricityEstimate; }
    }
    
    // Main class getters and setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public BillReminderData getReminder() { return reminder; }
    public void setReminder(BillReminderData reminder) { this.reminder = reminder; }
    
    public MaintenanceReminderData getMaintenanceReminder() { return maintenanceReminder; }
    public void setMaintenanceReminder(MaintenanceReminderData maintenanceReminder) { this.maintenanceReminder = maintenanceReminder; }
    
    public BudgetAlertData getBudgetAlerts() { return budgetAlerts; }
    public void setBudgetAlerts(BudgetAlertData budgetAlerts) { this.budgetAlerts = budgetAlerts; }
}
