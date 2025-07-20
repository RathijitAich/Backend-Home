# Bill Reminders Backend API

This backend provides comprehensive API endpoints for managing bill reminders, maintenance reminders, budget alerts, and email notifications for a home management system.

## Features

- **Bill Reminders**: Create, read, update, delete bill reminders with due dates
- **Maintenance Reminders**: Schedule and manage device maintenance tasks
- **Budget Alerts**: Track spending vs budget and get alerts when approaching limits
- **Email Notifications**: Automatic email notifications for due reminders and budget alerts
- **Scheduled Tasks**: Daily automatic checking and notification sending

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Bill Reminders

#### Create Bill Reminder
- **POST** `/bill-reminders`
- **Body**:
```json
{
  "userEmail": "user@example.com",
  "reminder": {
    "type": "electricity",
    "title": "Monthly Electric Bill",
    "dueDate": "2024-01-15",
    "amount": 1500.0,
    "frequency": "monthly",
    "description": "Monthly electricity bill payment"
  }
}
```

#### Get User's Bill Reminders
- **GET** `/bill-reminders/{userEmail}`
- **Response**:
```json
{
  "success": true,
  "reminders": [
    {
      "id": 1,
      "type": "electricity",
      "title": "Monthly Electric Bill",
      "dueDate": "2024-01-15",
      "amount": 1500.0,
      "frequency": "monthly",
      "description": "Monthly electricity bill payment",
      "userEmail": "user@example.com",
      "created": "2024-01-01T10:00:00"
    }
  ]
}
```

#### Delete Bill Reminder
- **DELETE** `/bill-reminders/{userEmail}/{id}`

### Maintenance Reminders

#### Create Maintenance Reminder
- **POST** `/maintenance-reminders`
- **Body**:
```json
{
  "userEmail": "user@example.com",
  "maintenanceReminder": {
    "device": "Air Conditioner",
    "task": "Filter Cleaning",
    "frequency": "monthly",
    "lastDone": "2024-01-01",
    "nextDue": "2024-02-01"
  }
}
```

#### Get User's Maintenance Reminders
- **GET** `/maintenance-reminders/{userEmail}`

#### Delete Maintenance Reminder
- **DELETE** `/maintenance-reminders/{userEmail}/{id}`

### Budget Alerts

#### Save Budget Data
- **POST** `/budget-alerts`
- **Body**:
```json
{
  "userEmail": "user@example.com",
  "budgetAlerts": {
    "groceryBudget": 5000.0,
    "grocerySpent": 3500.0,
    "electricityBudget": 2000.0,
    "electricityEstimate": 1800.0
  }
}
```

#### Get User's Budget Data
- **GET** `/budget-alerts/{userEmail}`

### Email Notifications

#### Check and Send Due Reminders
- **POST** `/check-due-reminders`
- **Body**:
```json
{
  "userEmail": "user@example.com"
}
```

#### Send Test Email
- **POST** `/test-email`
- **Body**:
```json
{
  "userEmail": "user@example.com"
}
```

## Data Models

### BillReminder
```java
{
  "id": Long,
  "type": String, // electricity, gas, water, internet, other
  "title": String,
  "dueDate": LocalDate,
  "amount": Double,
  "frequency": String, // monthly, quarterly, yearly, one-time
  "description": String,
  "userEmail": String,
  "created": LocalDateTime
}
```

### MaintenanceReminder
```java
{
  "id": Long,
  "device": String,
  "task": String,
  "frequency": String, // weekly, monthly, quarterly, yearly
  "lastDone": LocalDate,
  "nextDue": LocalDate,
  "userEmail": String
}
```

### BudgetAlert
```java
{
  "id": Long,
  "userEmail": String,
  "groceryBudget": Double,
  "grocerySpent": Double,
  "electricityBudget": Double,
  "electricityEstimate": Double
}
```

## Email Notifications

The system automatically sends email notifications for:

1. **Bill Reminders**: When bills are due today
2. **Maintenance Reminders**: When maintenance tasks are due today
3. **Budget Alerts**: When spending exceeds 90% of budget

### Scheduled Tasks

- **Daily at 9:00 AM**: Check for due bills and maintenance tasks
- **Daily at 10:00 AM**: Check for budget alerts
- **Hourly**: Log status of due reminders (for monitoring)

## Database Schema

The application uses MySQL database with the following tables:

### bill_reminders
```sql
CREATE TABLE bill_reminders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    due_date DATE NOT NULL,
    amount DOUBLE NOT NULL,
    frequency VARCHAR(255) NOT NULL,
    description TEXT,
    user_email VARCHAR(255) NOT NULL,
    created DATETIME NOT NULL
);
```

### maintenance_reminders
```sql
CREATE TABLE maintenance_reminders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device VARCHAR(255) NOT NULL,
    task VARCHAR(255) NOT NULL,
    frequency VARCHAR(255) NOT NULL,
    last_done DATE,
    next_due DATE NOT NULL,
    user_email VARCHAR(255) NOT NULL
);
```

### budget_alerts
```sql
CREATE TABLE budget_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL UNIQUE,
    grocery_budget DOUBLE NOT NULL DEFAULT 0.0,
    grocery_spent DOUBLE NOT NULL DEFAULT 0.0,
    electricity_budget DOUBLE NOT NULL DEFAULT 0.0,
    electricity_estimate DOUBLE NOT NULL DEFAULT 0.0
);
```

## Setup Instructions

1. **Database Configuration**:
   - Create a MySQL database named `HomeManagement`
   - Update credentials in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/HomeManagement
   spring.datasource.username=root
   spring.datasource.password=1234
   spring.jpa.hibernate.ddl-auto=update
   ```

2. **Email Configuration** (Optional):
   Add email configuration to `application.properties`:
   ```properties
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access APIs**:
   Server runs on `http://localhost:8080`

## CORS Configuration

The API is configured to accept requests from `http://localhost:3000` for React frontend integration.

## Error Handling

All endpoints return standardized JSON responses:

**Success Response**:
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {...}
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Error description",
  "details": "Additional error details"
}
```

## Testing

Use tools like Postman or curl to test the API endpoints. The application includes comprehensive error handling and validation.

## Dependencies

- Spring Boot 3.4.5
- Spring Data JPA
- Spring Web
- Spring Mail
- MySQL Connector
- Spring WebSocket (for future real-time features)

## Future Enhancements

- Real email integration with SMTP providers
- Push notifications
- Recurring reminder automation
- Advanced budget analytics
- Mobile app API support
