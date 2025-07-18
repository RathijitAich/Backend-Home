package com.example.demo.Controller;

import com.example.demo.Entity.BillReminder;
import com.example.demo.Entity.BudgetAlert;
import com.example.demo.Entity.MaintenanceReminder;
import com.example.demo.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BillReminderController {
    
    @Autowired
    private ReminderService reminderService;
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend is running");
        response.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
    // Bill Reminder Endpoints
    @PostMapping("/bill-reminders")
    public ResponseEntity<BillReminder> saveBillReminder(@RequestBody BillReminder reminder) {
        try {
            System.out.println("Received bill reminder: " + reminder.getTitle() + " for user: " + reminder.getUserEmail());
            
            // Validate required fields
            if (reminder.getUserEmail() == null || reminder.getUserEmail().trim().isEmpty()) {
                System.err.println("Error: userEmail is required");
                return ResponseEntity.badRequest().body(null);
            }
            if (reminder.getTitle() == null || reminder.getTitle().trim().isEmpty()) {
                System.err.println("Error: title is required");
                return ResponseEntity.badRequest().body(null);
            }
            if (reminder.getDueDate() == null) {
                System.err.println("Error: dueDate is required");
                return ResponseEntity.badRequest().body(null);
            }
            
            // For new records from frontend (which might send a timestamp as ID), reset the ID to null
            // so Hibernate treats it as a new entity
            if (reminder.getId() != null && reminder.getId() > 100000000L) { // Timestamp-like ID
                System.out.println("Resetting ID for new entity (was: " + reminder.getId() + ")");
                reminder.setId(null);
            }
            
            // Set created timestamp if not provided
            if (reminder.getCreated() == null) {
                reminder.setCreated(java.time.LocalDateTime.now());
            }
            
            BillReminder savedReminder = reminderService.saveBillReminder(reminder);
            System.out.println("Successfully saved bill reminder with ID: " + savedReminder.getId());
            return ResponseEntity.ok(savedReminder);
            
        } catch (Exception e) {
            System.err.println("Error saving bill reminder: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/bill-reminders/{userEmail}")
    public ResponseEntity<List<BillReminder>> getBillReminders(@PathVariable String userEmail) {
        try {
            List<BillReminder> reminders = reminderService.getBillRemindersByUser(userEmail);
            return ResponseEntity.ok(reminders);
            
        } catch (Exception e) {
            System.err.println("Error retrieving bill reminders: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/bill-reminders/{userEmail}/{id}")
    public ResponseEntity<Map<String, Object>> deleteBillReminder(@PathVariable String userEmail, @PathVariable Long id) {
        try {
            boolean deleted = reminderService.deleteBillReminder(userEmail, id);
            
            Map<String, Object> response = new HashMap<>();
            if (deleted) {
                response.put("success", true);
                response.put("message", "Bill reminder deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Bill reminder not found or unauthorized");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting bill reminder: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting bill reminder: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Maintenance Reminder Endpoints
    @PostMapping("/maintenance-reminders")
    public ResponseEntity<MaintenanceReminder> saveMaintenanceReminder(@RequestBody MaintenanceReminder reminder) {
        try {
            System.out.println("Received maintenance reminder: " + reminder.getDevice() + " - " + reminder.getTask() + " for user: " + reminder.getUserEmail());
            
            // Validate required fields
            if (reminder.getUserEmail() == null || reminder.getUserEmail().trim().isEmpty()) {
                System.err.println("Error: userEmail is required");
                return ResponseEntity.badRequest().body(null);
            }
            if (reminder.getDevice() == null || reminder.getDevice().trim().isEmpty()) {
                System.err.println("Error: device is required");
                return ResponseEntity.badRequest().body(null);
            }
            if (reminder.getTask() == null || reminder.getTask().trim().isEmpty()) {
                System.err.println("Error: task is required");
                return ResponseEntity.badRequest().body(null);
            }
            if (reminder.getNextDue() == null) {
                System.err.println("Error: nextDue is required");
                return ResponseEntity.badRequest().body(null);
            }
            
            // For new records from frontend (which might send a timestamp as ID), reset the ID to null
            // so Hibernate treats it as a new entity
            if (reminder.getId() != null && reminder.getId() > 100000000L) { // Timestamp-like ID
                System.out.println("Resetting ID for new entity (was: " + reminder.getId() + ")");
                reminder.setId(null);
            }
            
            MaintenanceReminder savedReminder = reminderService.saveMaintenanceReminder(reminder);
            System.out.println("Successfully saved maintenance reminder with ID: " + savedReminder.getId());
            return ResponseEntity.ok(savedReminder);
            
        } catch (Exception e) {
            System.err.println("Error saving maintenance reminder: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/maintenance-reminders/{userEmail}")
    public ResponseEntity<List<MaintenanceReminder>> getMaintenanceReminders(@PathVariable String userEmail) {
        try {
            List<MaintenanceReminder> reminders = reminderService.getMaintenanceRemindersByUser(userEmail);
            return ResponseEntity.ok(reminders);
            
        } catch (Exception e) {
            System.err.println("Error retrieving maintenance reminders: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/maintenance-reminders/{userEmail}/{id}")
    public ResponseEntity<Map<String, Object>> deleteMaintenanceReminder(@PathVariable String userEmail, @PathVariable Long id) {
        try {
            boolean deleted = reminderService.deleteMaintenanceReminder(userEmail, id);
            
            Map<String, Object> response = new HashMap<>();
            if (deleted) {
                response.put("success", true);
                response.put("message", "Maintenance reminder deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Maintenance reminder not found or unauthorized");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting maintenance reminder: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting maintenance reminder: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Budget Alert Endpoints
    @PostMapping("/budget-alerts")
    public ResponseEntity<BudgetAlert> saveBudgetAlert(@RequestBody BudgetAlert budgetAlert) {
        try {
            System.out.println("Received budget alert for user: " + budgetAlert.getUserEmail());
            
            // Validate required fields
            if (budgetAlert.getUserEmail() == null || budgetAlert.getUserEmail().trim().isEmpty()) {
                System.err.println("Error: userEmail is required");
                return ResponseEntity.badRequest().body(null);
            }
            
            BudgetAlert savedBudgetAlert = reminderService.saveBudgetAlert(budgetAlert);
            System.out.println("Successfully saved budget alert with ID: " + savedBudgetAlert.getId());
            return ResponseEntity.ok(savedBudgetAlert);
            
        } catch (Exception e) {
            System.err.println("Error saving budget alert: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/budget-alerts/{userEmail}")
    public ResponseEntity<BudgetAlert> getBudgetAlert(@PathVariable String userEmail) {
        try {
            Optional<BudgetAlert> budgetAlert = reminderService.getBudgetAlertByUser(userEmail);
            
            if (budgetAlert.isPresent()) {
                return ResponseEntity.ok(budgetAlert.get());
            } else {
                // Return default budget alert if none exists
                BudgetAlert defaultAlert = new BudgetAlert();
                defaultAlert.setUserEmail(userEmail);
                defaultAlert.setGroceryBudget(0.0);
                defaultAlert.setGrocerySpent(0.0);
                defaultAlert.setElectricityBudget(0.0);
                defaultAlert.setElectricityEstimate(0.0);
                return ResponseEntity.ok(defaultAlert);
            }
            
        } catch (Exception e) {
            System.err.println("Error retrieving budget alert: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // Email Notification Endpoints
    @PostMapping("/check-due-reminders")
    public ResponseEntity<Map<String, Object>> checkDueReminders(@RequestBody Map<String, Object> requestBody) {
        try {
            String userEmail = (String) requestBody.get("userEmail");
            
            if (userEmail == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Missing userEmail");
                return ResponseEntity.badRequest().body(response);
            }
            
            int notificationsSent = reminderService.checkAndSendDueReminders(userEmail);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Due reminders checked successfully");
            response.put("notificationsSent", notificationsSent);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error checking due reminders: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/test-email")
    public ResponseEntity<Map<String, Object>> testEmail(@RequestBody Map<String, Object> requestBody) {
        try {
            String userEmail = (String) requestBody.get("userEmail");
            
            if (userEmail == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Missing userEmail");
                return ResponseEntity.badRequest().body(response);
            }
            
            reminderService.sendTestEmail(userEmail);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Test email sent successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Error sending test email");
            response.put("details", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}