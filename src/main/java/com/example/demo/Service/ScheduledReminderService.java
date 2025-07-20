package com.example.demo.Service;

import com.example.demo.Entity.BillReminder;
import com.example.demo.Entity.BudgetAlert;
import com.example.demo.Entity.MaintenanceReminder;
import com.example.demo.Repository.BillReminderRepository;
import com.example.demo.Repository.BudgetAlertRepository;
import com.example.demo.Repository.MaintenanceReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class ScheduledReminderService {
    
    private static final Logger logger = Logger.getLogger(ScheduledReminderService.class.getName());
    
    @Autowired
    private BillReminderRepository billReminderRepository;
    
    @Autowired
    private MaintenanceReminderRepository maintenanceReminderRepository;
    
    @Autowired
    private BudgetAlertRepository budgetAlertRepository;
    
    @Autowired
    private EmailService emailService;
    
    // Run every day at 9:00 AM to check for due reminders
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkDailyReminders() {
        logger.info("Running daily reminder check...");
        
        LocalDate today = LocalDate.now();
        Set<String> notifiedUsers = new HashSet<>();
        
        // Check bill reminders due today
        List<BillReminder> dueBills = billReminderRepository.findByDueDate(today);
        for (BillReminder bill : dueBills) {
            if (!notifiedUsers.contains(bill.getUserEmail())) {
                List<BillReminder> userDueBills = billReminderRepository.findOverdueRemindersByUser(today, bill.getUserEmail());
                emailService.sendBillReminderEmail(bill.getUserEmail(), userDueBills);
                notifiedUsers.add(bill.getUserEmail());
            }
        }
        
        // Check maintenance reminders due today
        List<MaintenanceReminder> dueMaintenance = maintenanceReminderRepository.findByNextDue(today);
        Set<String> maintenanceNotifiedUsers = new HashSet<>();
        for (MaintenanceReminder maintenance : dueMaintenance) {
            if (!maintenanceNotifiedUsers.contains(maintenance.getUserEmail())) {
                List<MaintenanceReminder> userDueMaintenance = maintenanceReminderRepository.findOverdueMaintenanceByUser(today, maintenance.getUserEmail());
                emailService.sendMaintenanceReminderEmail(maintenance.getUserEmail(), userDueMaintenance);
                maintenanceNotifiedUsers.add(maintenance.getUserEmail());
            }
        }
        
        logger.info("Daily reminder check completed. Bill notifications: " + notifiedUsers.size() + 
                   ", Maintenance notifications: " + maintenanceNotifiedUsers.size());
    }
    
    // Run every day at 10:00 AM to check for budget alerts
    @Scheduled(cron = "0 0 10 * * ?")
    public void checkBudgetAlerts() {
        logger.info("Running daily budget alert check...");
        
        List<BudgetAlert> budgetExceededUsers = budgetAlertRepository.findBudgetExceededUsers();
        int notificationsSent = 0;
        
        for (BudgetAlert budgetAlert : budgetExceededUsers) {
            emailService.sendBudgetAlertEmail(budgetAlert.getUserEmail(), budgetAlert);
            notificationsSent++;
        }
        
        logger.info("Budget alert check completed. Notifications sent: " + notificationsSent);
    }
    
    // Run every hour to check for immediate notifications (for testing purposes)
    @Scheduled(fixedRate = 3600000) // 1 hour = 3600000 ms
    public void hourlyReminderCheck() {
        logger.info("Running hourly reminder check for immediate notifications...");
        
        LocalDate today = LocalDate.now();
        
        // Check for bills due today
        List<BillReminder> dueBills = billReminderRepository.findByDueDate(today);
        if (!dueBills.isEmpty()) {
            logger.info("Found " + dueBills.size() + " bills due today");
        }
        
        // Check for maintenance due today
        List<MaintenanceReminder> dueMaintenance = maintenanceReminderRepository.findByNextDue(today);
        if (!dueMaintenance.isEmpty()) {
            logger.info("Found " + dueMaintenance.size() + " maintenance tasks due today");
        }
        
        // This is mainly for logging - actual notifications are sent by the daily scheduled task
    }
}