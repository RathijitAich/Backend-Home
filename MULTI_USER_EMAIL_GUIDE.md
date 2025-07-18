# Multi-User Email Setup Guide

## How the Email System Works

### Current Architecture:
- **FROM Email**: System email (e.g., `homemanagement.system@gmail.com`)
- **TO Email**: Each user's individual email address (from their login/registration)
- **Authentication**: Uses the system email credentials to send to any user

## Setup Options

### Option 1: Create a Dedicated System Gmail Account (Recommended)

1. **Create a new Gmail account** for your system:
   - Email: `homemanagement.system@gmail.com` (or similar)
   - This will be used ONLY for sending notifications

2. **Set up App Password** for the system account:
   - Go to Google Account settings for the system email
   - Enable 2-Step Verification
   - Generate App Password for "Mail"
   - Copy the 16-character password

3. **Update application.properties**:
   ```properties
   spring.mail.username=homemanagement.system@gmail.com
   spring.mail.password=your-system-email-app-password
   app.email.from=homemanagement.system@gmail.com
   app.email.enabled=true
   ```

### Option 2: Use Your Personal Gmail (Quick Test)

For testing purposes, you can use your personal Gmail:

```properties
spring.mail.username=ddshuvo124@gmail.com
spring.mail.password=your-personal-app-password
app.email.from=Home Management System <ddshuvo124@gmail.com>
app.email.enabled=true
```

### Option 3: Keep Testing Mode (No Real Emails)

```properties
app.email.enabled=false
```

## How Users Receive Emails

The system automatically sends emails to users based on their stored email address:

### When a user tests email:
- **User logs in as**: `user1@example.com`
- **System sends email FROM**: `homemanagement.system@gmail.com`  
- **User receives email AT**: `user1@example.com`

### When another user tests email:
- **User logs in as**: `user2@example.com`
- **System sends email FROM**: `homemanagement.system@gmail.com`
- **User receives email AT**: `user2@example.com`

## Email Flow Diagram

```
System Gmail Account (homemanagement.system@gmail.com)
                    ↓ (sends from)
            Spring Boot Application
                    ↓ (determines recipient from user session)
User 1 (user1@email.com) ← Gets bill reminders
User 2 (user2@email.com) ← Gets bill reminders  
User 3 (user3@email.com) ← Gets bill reminders
```

## Security Best Practices

### 1. Environment Variables (Recommended)
Instead of hardcoding credentials:

```properties
spring.mail.username=${SYSTEM_EMAIL_USERNAME:homemanagement.system@gmail.com}
spring.mail.password=${SYSTEM_EMAIL_PASSWORD:your-app-password}
app.email.from=${SYSTEM_EMAIL_FROM:homemanagement.system@gmail.com}
```

Set environment variables:
```bash
set SYSTEM_EMAIL_USERNAME=homemanagement.system@gmail.com
set SYSTEM_EMAIL_PASSWORD=your-16-char-app-password
set SYSTEM_EMAIL_FROM=homemanagement.system@gmail.com
```

### 2. Create .env file (Alternative)
Create a `.env` file (add to .gitignore):
```
SYSTEM_EMAIL=homemanagement.system@gmail.com
SYSTEM_EMAIL_PASSWORD=your-app-password
```

## Professional Email Services (Production)

For production, consider:

### 1. SendGrid
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=your-sendgrid-api-key
```

### 2. AWS SES
```properties
spring.mail.host=email-smtp.us-east-1.amazonaws.com
spring.mail.port=587
spring.mail.username=your-aws-smtp-username
spring.mail.password=your-aws-smtp-password
```

### 3. Microsoft 365
```properties
spring.mail.host=smtp.office365.com
spring.mail.port=587
spring.mail.username=system@yourcompany.com
spring.mail.password=your-password
```

## Current Configuration Status

✅ **FROM email**: System email (can send to any user)  
✅ **TO email**: Dynamic (based on logged-in user)  
✅ **Multi-user support**: Ready  
⚠️ **Email enabled**: Currently disabled for testing  

## Next Steps

1. **Choose your setup option** from above
2. **Update the credentials** in application.properties
3. **Test with different user accounts** to verify multi-user functionality
4. **Enable email** by setting `app.email.enabled=true`

## Testing Multi-User Functionality

1. **Login as User 1** → Click "Test Email" → Check User 1's inbox
2. **Login as User 2** → Click "Test Email" → Check User 2's inbox
3. **Both should receive emails** from the same system account
