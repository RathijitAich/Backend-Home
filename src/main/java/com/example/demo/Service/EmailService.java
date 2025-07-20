package com.example.demo.Service;

import com.example.demo.Entity.BillReminder;
import com.example.demo.Entity.BudgetAlert;
import com.example.demo.Entity.MaintenanceReminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmailService {
    
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.email.from:noreply@homemanagement.com}")
    private String fromEmail;
    
    @Value("${app.email.enabled:false}")
    private boolean emailEnabled;
    
    public void sendBillReminderEmail(String userEmail, List<BillReminder> dueReminders) {
        logger.info("Sending bill reminder email to: " + userEmail);
        
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear User,\n\n");
        emailContent.append("You have the following bills due today:\n\n");
        
        for (BillReminder reminder : dueReminders) {
            emailContent.append("- ").append(reminder.getTitle())
                       .append(" (").append(reminder.getType()).append(")")
                       .append(" - Amount: ৳").append(reminder.getAmount())
                       .append(" - Due: ").append(reminder.getDueDate())
                       .append("\n");
            
            if (reminder.getDescription() != null && !reminder.getDescription().isEmpty()) {
                emailContent.append("  Description: ").append(reminder.getDescription()).append("\n");
            }
            emailContent.append("\n");
        }
        
        emailContent.append("Please make sure to pay these bills on time to avoid late fees.\n\n");
        emailContent.append("Best regards,\nHome Management System");
        
        sendEmail(userEmail, "Bill Reminder - Due Today", emailContent.toString());
    }
    
    public void sendMaintenanceReminderEmail(String userEmail, List<MaintenanceReminder> dueReminders) {
        logger.info("Sending maintenance reminder email to: " + userEmail);
        
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear User,\n\n");
        emailContent.append("You have the following maintenance tasks due today:\n\n");
        
        for (MaintenanceReminder reminder : dueReminders) {
            emailContent.append("- ").append(reminder.getDevice())
                       .append(" - ").append(reminder.getTask())
                       .append(" (").append(reminder.getFrequency()).append(")")
                       .append(" - Due: ").append(reminder.getNextDue())
                       .append("\n");
        }
        
        emailContent.append("\nPlease complete these maintenance tasks to keep your devices running efficiently.\n\n");
        emailContent.append("Best regards,\nHome Management System");
        
        sendEmail(userEmail, "Maintenance Reminder - Due Today", emailContent.toString());
    }
    
    public void sendBudgetAlertEmail(String userEmail, BudgetAlert budgetAlert) {
        logger.info("Sending budget alert email to: " + userEmail);
        
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear User,\n\n");
        emailContent.append("Budget Alert: You have exceeded 90% of your budget!\n\n");
        
        // Check grocery budget
        double groceryPercentage = (budgetAlert.getGrocerySpent() / budgetAlert.getGroceryBudget()) * 100;
        if (groceryPercentage >= 90) {
            emailContent.append("Grocery Budget Alert:\n");
            emailContent.append("- Budget: ৳").append(budgetAlert.getGroceryBudget()).append("\n");
            emailContent.append("- Spent: ৳").append(budgetAlert.getGrocerySpent()).append("\n");
            emailContent.append("- Percentage: ").append(String.format("%.1f", groceryPercentage)).append("%\n\n");
        }
        
        // Check electricity budget
        double electricityPercentage = (budgetAlert.getElectricityEstimate() / budgetAlert.getElectricityBudget()) * 100;
        if (electricityPercentage >= 90) {
            emailContent.append("Electricity Budget Alert:\n");
            emailContent.append("- Budget: ৳").append(budgetAlert.getElectricityBudget()).append("\n");
            emailContent.append("- Estimated: ৳").append(String.format("%.0f", budgetAlert.getElectricityEstimate())).append("\n");
            emailContent.append("- Percentage: ").append(String.format("%.1f", electricityPercentage)).append("%\n\n");
        }
        
        emailContent.append("Please review your spending and consider adjusting your budget or expenses.\n\n");
        emailContent.append("Best regards,\nHome Management System");
        
        sendEmail(userEmail, "Budget Alert - Spending Limit Exceeded", emailContent.toString());
    }
    
    public void sendTestEmail(String userEmail) {
        logger.info("Sending test email to: " + userEmail);
        logger.info("Email enabled: " + emailEnabled);
        logger.info("From email: " + fromEmail);
        
        String emailContent = "Dear User,\n\n" +
                             "This is a test email from the Home Management System.\n\n" +
                             "If you receive this email, the email notification system is working correctly.\n\n" +
                             "Configuration Details:\n" +
                             "- From Email: " + fromEmail + "\n" +
                             "- Email Enabled: " + emailEnabled + "\n\n" +
                             "Best regards,\nHome Management System";
        
        sendEmail(userEmail, "Test Email - Home Management System", emailContent);
    }
    
    private void sendEmail(String toEmail, String subject, String content) {
        if (!emailEnabled) {
            logger.warning("Email is disabled. Would have sent email to: " + toEmail + " with subject: " + subject);
            logger.info("Email content:\n" + content);
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            logger.info("Email sent successfully to: " + toEmail);
            
        } catch (Exception e) {
            logger.severe("Failed to send email to " + toEmail + ": " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}