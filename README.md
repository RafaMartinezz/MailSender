# Email sender with Jakarta Mail

This project demonstrates how to send an email using Jakarta Mail (formerly JavaMail). It is set up to use an SMTP server for sending emails with Java, showcasing SMTP session configuration, message creation, and exception handling for common email sending issues.

## Project Overview

The `SenderEmail` class configures and initializes an SMTP connection to Gmail’s SMTP server and sends an email message to a specified recipient. This project handles all necessary configurations for the SMTP connection and error handling for issues such as authentication failure, connection issues, and provider mismatches.

## Requirements

- **Java 17** or higher
- **Jakarta Mail 2.0.1** (via Maven dependency)
- **Jakarta Activation API 2.0.1** (for handling MIME types)

## Setup Instructions

1. **Clone this repository** or copy the project files to your local machine.
2. **Configure the Sender’s Email**:
   - Update the `mail.smtp.mail.sender` and `mail.smtp.user` properties in the `SenderEmail` class with your email address.
   - Update the `t.connect()` call with the correct email and app-specific password or SMTP credentials.

3. **Build the project**:
   - This is a Maven-based project, so you can build it using:
     ```sh
     mvn clean install
     ```

## Usage

To send an email, run the `SenderEmail` class. This will attempt to connect to Gmail's SMTP server, authenticate, and send an email with a subject and body specified in the code.

### Sample Execution

```java
public static void main(String args[]) {
    SenderEmail sender = new SenderEmail();
    sender.init(); // Initializes the configuration

    try {
        MimeMessage message = new MimeMessage(sender.session);
        message.setFrom(new InternetAddress("your-email@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("recipient-email@example.com"));
        message.setSubject("Test Email Subject");
        message.setText("This is a test email from Jakarta Mail.");
        
        Transport t = sender.session.getTransport("smtp");
        t.connect("your-email@gmail.com", "your-password");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

## Configuration

The SMTP connection properties are configured in the `init` method:

- **SMTP Host**: Gmail's SMTP server (`smtp.gmail.com`).
- **TLS**: Enabled for secure connection (`mail.smtp.starttls.enable` set to `true`).
- **SMTP Port**: Default SMTP port 25.
- **Authentication**: Enabled (`mail.smtp.auth` set to `true`), requiring valid credentials.

> **Note**: Ensure that less secure app access is enabled on the Gmail account if using basic authentication. Google may require using an App Password instead of the actual email password.

## Exception Handling

The project handles the following common exceptions:
- `IllegalWriteException`: For attempting to modify read-only fields.
- `NoSuchProviderException`: For missing email providers.
- `AuthenticationFailedException`: For incorrect email credentials.
- `SendFailedException`: For failures in the message sending process.
- `MessagingException`: A generic catch-all for email-related errors.

## Dependencies

The following dependencies are managed through Maven:

```xml
<dependencies>
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>jakarta.mail</artifactId>
        <version>2.0.1</version>
    </dependency>
    <dependency>
        <groupId>jakarta.activation</groupId>
        <artifactId>jakarta.activation-api</artifactId>
        <version>2.0.1</version>
    </dependency>
</dependencies>
```
