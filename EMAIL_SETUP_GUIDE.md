# Email Configuration Guide

## Current Status
The email functionality is now implemented but requires proper configuration to actually send emails.

## Quick Setup Options

### Option 1: Use Gmail (Recommended for testing)

1. **Update application.properties:**
   ```properties
   # Replace with your Gmail credentials
   spring.mail.username=your-gmail@gmail.com
   spring.mail.password=your-app-password
   app.email.from=your-gmail@gmail.com
   app.email.enabled=true
   ```

2. **Generate Gmail App Password:**
   - Go to Google Account settings
   - Enable 2-Factor Authentication
   - Generate an "App Password" for "Mail"
   - Use this app password (not your regular password)

### Option 2: Disable Email (Testing Mode)
If you just want to test without actual emails:
```properties
app.email.enabled=false
```
This will log email content to console instead of sending.

### Option 3: Use Other Email Providers

#### Outlook/Hotmail
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
```

#### Yahoo Mail
```properties
spring.mail.host=smtp.mail.yahoo.com
spring.mail.port=587
spring.mail.username=your-email@yahoo.com
spring.mail.password=your-password
```

## Security Notes

⚠️ **Never commit real credentials to Git!**

### Recommended: Use Environment Variables
Instead of hardcoding in application.properties:

```properties
spring.mail.username=${EMAIL_USERNAME:your-email@gmail.com}
spring.mail.password=${EMAIL_PASSWORD:your-app-password}
app.email.from=${EMAIL_FROM:your-email@gmail.com}
```

Then set environment variables:
```bash
set EMAIL_USERNAME=your-email@gmail.com
set EMAIL_PASSWORD=your-app-password
set EMAIL_FROM=your-email@gmail.com
```

## Testing

1. **Start the application**
2. **Click "Test Email" button** in the frontend
3. **Check console logs** if email is disabled
4. **Check your inbox** if email is enabled

## Troubleshooting

### Common Issues:

1. **"Authentication failed"**
   - Ensure 2FA is enabled for Gmail
   - Use App Password, not regular password
   - Check username/password are correct

2. **"Could not connect to SMTP host"**
   - Check internet connection
   - Verify SMTP host and port
   - Some networks block SMTP ports

3. **"Email sent successfully" but no email received**
   - Check spam folder
   - Verify recipient email address
   - Check email provider logs

### Debug Steps:

1. Enable debug logging:
   ```properties
   logging.level.org.springframework.mail=DEBUG
   ```

2. Check application logs for detailed error messages

3. Test with a simple email first

## Current Configuration

The application is configured to send emails from:
- **From Address**: Value of `app.email.from` property
- **Email Enabled**: Value of `app.email.enabled` property
- **SMTP Settings**: Gmail by default (smtp.gmail.com:587)

## Email Types Sent

1. **Bill Reminders** - When bills are due today
2. **Maintenance Reminders** - When maintenance tasks are due
3. **Budget Alerts** - When spending exceeds 90% of budget
4. **Test Emails** - For testing configuration
