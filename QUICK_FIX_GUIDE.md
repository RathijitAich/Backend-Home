# Quick Fix for Email Issue

## Immediate Solution

You have two options to fix this quickly:

### Option 1: Disable Email (Fastest - for testing)
Your `application.properties` is already set to `app.email.enabled=false`, so emails will be logged instead of sent.

### Option 2: Set up Gmail properly

1. **Get Gmail App Password:**
   - Go to https://myaccount.google.com/security
   - Enable 2-Step Verification (if not already enabled)
   - Search for "App passwords"
   - Select "Mail" and generate a password
   - Copy the 16-character password (like: `abcd efgh ijkl mnop`)

2. **Update application.properties:**
   ```properties
   spring.mail.password=your-16-character-app-password-here
   app.email.enabled=true
   ```

## Current Status Check

1. **Start your backend** - it should run on port 5000 now
2. **Frontend should connect to** `http://localhost:5000` (not 8080)
3. **Test the connection** with the test email button

## If Still Getting Port 8080 Error

Your backend might be running on port 8080. Check the console when you start the application.

If it says `Tomcat started on port(s): 8080`, then update your frontend code:

**In your React component**, change:
```javascript
// Change from:
const response = await fetch('http://localhost:5000/api/test-email', {

// To:
const response = await fetch('http://localhost:8080/api/test-email', {
```

## Test Steps

1. **Start backend** (should say "Tomcat started on port(s): 5000")
2. **Click "Test Email"** in frontend
3. **Check console logs** - you should see email content printed
4. **If email is enabled and configured correctly**, check your inbox

## Expected Console Output (when email disabled)
```
Email is disabled. Would have sent email to: ddshuvo124@gmail.com with subject: Test Email - Home Management System
Email content:
Dear User,

This is a test email from the Home Management System...
```

## Expected Response (when working)
```json
{
  "success": true,
  "message": "Test email sent successfully"
}
```
