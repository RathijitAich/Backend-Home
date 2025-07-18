package com.example.demo.Service;

import com.example.demo.Entity.BillReminder;
import com.example.demo.Entity.BudgetAlert;
import com.example.demo.Entity.MaintenanceReminder;
import com.example.demo.Repository.BillReminderRepository;
import com.example.demo.Repository.BudgetAlertRepository;
import com.example.demo.Repository.MaintenanceReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    
    @Autowired
    private BillReminderRepository billReminderRepository;
    
    @Autowired
    private MaintenanceReminderRepository maintenanceReminderRepository;
    
    @Autowired
    private BudgetAlertRepository budgetAlertRepository;
    
    @Autowired
    private EmailService emailService;
    
    // Bill Reminder Methods
    public BillReminder saveBillReminder(BillReminder reminder) {
        reminder.setCreated(LocalDateTime.now());
        return billReminderRepository.save(reminder);
    }
    
    public List<BillReminder> getBillRemindersByUser(String userEmail) {
        return billReminderRepository.findByUserEmail(userEmail);
    }
    
    public boolean deleteBillReminder(String userEmail, Long id) {
        Optional<BillReminder> reminder = billReminderRepository.findByIdAndUserEmail(id, userEmail);
        if (reminder.isPresent()) {
            billReminderRepository.delete(reminder.get());
            return true;
        }
        return false;
    }
    
    // Maintenance Reminder Methods
    public MaintenanceReminder saveMaintenanceReminder(MaintenanceReminder reminder) {
        return maintenanceReminderRepository.save(reminder);
    }
    
    public List<MaintenanceReminder> getMaintenanceRemindersByUser(String userEmail) {
        return maintenanceReminderRepository.findByUserEmail(userEmail);
    }
    
    public boolean deleteMaintenanceReminder(String userEmail, Long id) {
        Optional<MaintenanceReminder> reminder = maintenanceReminderRepository.findByIdAndUserEmail(id, userEmail);
        if (reminder.isPresent()) {
            maintenanceReminderRepository.delete(reminder.get());
            return true;
        }
        return false;
    }
    
    // Budget Alert Methods
    public BudgetAlert saveBudgetAlert(BudgetAlert budgetAlert) {
        Optional<BudgetAlert> existing = budgetAlertRepository.findByUserEmail(budgetAlert.getUserEmail());
        if (existing.isPresent()) {
            BudgetAlert existingAlert = existing.get();
            existingAlert.setGroceryBudget(budgetAlert.getGroceryBudget());
            existingAlert.setGrocerySpent(budgetAlert.getGrocerySpent());
            existingAlert.setElectricityBudget(budgetAlert.getElectricityBudget());
            existingAlert.setElectricityEstimate(budgetAlert.getElectricityEstimate());
            return budgetAlertRepository.save(existingAlert);
        } else {
            return budgetAlertRepository.save(budgetAlert);
        }
    }
    
    public Optional<BudgetAlert> getBudgetAlertByUser(String userEmail) {
        return budgetAlertRepository.findByUserEmail(userEmail);
    }
    
    // Email Notification Methods
    public int checkAndSendDueReminders(String userEmail) {
        LocalDate today = LocalDate.now();
        int notificationsSent = 0;
        
        // Check bill reminders due today
        List<BillReminder> dueBills = billReminderRepository.findOverdueRemindersByUser(today, userEmail);
        if (!dueBills.isEmpty()) {
            emailService.sendBillReminderEmail(userEmail, dueBills);
            notificationsSent++;
        }
        
        // Check maintenance reminders due today
        List<MaintenanceReminder> dueMaintenance = maintenanceReminderRepository.findOverdueMaintenanceByUser(today, userEmail);
        if (!dueMaintenance.isEmpty()) {
            emailService.sendMaintenanceReminderEmail(userEmail, dueMaintenance);
            notificationsSent++;
        }
        
        // Check budget alerts
        Optional<BudgetAlert> budgetAlert = budgetAlertRepository.findByUserEmail(userEmail);
        if (budgetAlert.isPresent()) {
            BudgetAlert alert = budgetAlert.get();
            boolean shouldAlert = false;
            
            // Check if grocery spending exceeds 90% of budget
            if (alert.getGroceryBudget() > 0 && 
                (alert.getGrocerySpent() / alert.getGroceryBudget()) >= 0.9) {
                shouldAlert = true;
            }
            
            // Check if electricity estimate exceeds 90% of budget
            if (alert.getElectricityBudget() > 0 && 
                (alert.getElectricityEstimate() / alert.getElectricityBudget()) >= 0.9) {
                shouldAlert = true;
            }
            
            if (shouldAlert) {
                emailService.sendBudgetAlertEmail(userEmail, alert);
                notificationsSent++;
            }
        }
        
        return notificationsSent;
    }
    
    public void sendTestEmail(String userEmail) {
        emailService.sendTestEmail(userEmail);
    }
}