package com.kart.service.utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHandler {
    private static final String EMAIL_USERNAME ="sreekanthharisree2000@gmail.com";  // admin email
    private static final String EMAIL_PASSWORD ="fgihtmwxkotmzoqc"; //this password should generate on app passwords in gmail account.

    public static void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
