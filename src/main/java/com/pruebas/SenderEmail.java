package com.pruebas;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * The SenderEmail class encapsulates functionality for sending emails
 * using Jakarta Mail, configuring and using an SMTP connection to send
 * messages.
 */
public class SenderEmail {
    /**
     * Configuration properties for the SMTP session.
     */
    private final Properties properties = new Properties();

    /**
     * Mail session used to send messages.
     */
    private Session session;

    /**
     * Initializes the SMTP session properties and creates the session.
     * Configures necessary SMTP server parameters such as host, port,
     * authentication, and other connection-specific details.
     */
    private void init() {
        // Gmail SMTP server configuration
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Sets the SMTP server host
        properties.put("mail.smtp.starttls.enable", "true"); // Enables TLS if available
        properties.put("mail.smtp.port", 25); // Standard SMTP port
        properties.put("mail.smtp.mail.sender", "xxxxxxx"); // Sender's address
        properties.put("mail.smtp.user", "xxxxx"); // SMTP authentication username
        properties.put("mail.smtp.auth", "true"); // Enables SMTP authentication

        // Get the default session instance with the configured properties
        session = Session.getDefaultInstance(properties);
    }

    /**
     * Main method to send an email. Configures the email message and handles
     * exceptions
     * related to email sending.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String args[]) {
        SenderEmail sender = new SenderEmail();

        sender.init(); // Initialize mail configuration
        try {
            // Create a new MIME message
            MimeMessage message = new MimeMessage(sender.session);

            // Set the sender's email address
            message.setFrom(new InternetAddress((String) sender.properties.get("mail.smtp.mail.sender")));

            // Add recipient's email address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("xxxxxxx"));

            // Set the subject of the email
            message.setSubject("Prueba de JavaMail de Rafa");

            // Set the email message content
            message.setText("Hola estoy probando JavaMail");

            // Establish a connection to the SMTP server and send the message
            Transport t = sender.session.getTransport("smtp");

            // Connect using SMTP authentication credentials
            t.connect((String) sender.properties.get("mail.smtp.user"), "xxxxxxx");

            // Send the message to all recipients
            t.sendMessage(message, message.getAllRecipients());

            // Close the transport connection
            t.close();
        } catch (IllegalWriteException w) {
            w.printStackTrace();
            System.out.println("Attempted to write to a read-only attribute");
        } catch (NoSuchProviderException pr) {
            pr.printStackTrace();
            System.out.println("Provider does not exist");
        } catch (AuthenticationFailedException fail) {
            fail.printStackTrace();
            System.out.println("Authentication failed");
        } catch (IllegalStateException ie) {
            ie.printStackTrace();
            System.out.println("The service is already connected");
        } catch (SendFailedException sf) {
            sf.printStackTrace();
            System.out.println("Failed to send email");
        } catch (MessagingException me) {
            me.printStackTrace();
            System.out.println("Generic messaging error");
        }
    }
}
