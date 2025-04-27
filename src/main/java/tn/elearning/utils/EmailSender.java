package tn.elearning.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final String SMTP_HOST = "smtp.gmail.com"; // Exemple avec Gmail
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "bendhifallahines@gmail.com"; // Remplacez par votre email
    private static final String PASSWORD = "sydv wqmg fnmy zhco"; // Mot de passe d'application Gmail

    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        // 1. Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // 2. Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // 3. Création du message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // 4. Envoi de l'email
        Transport.send(message);
    }
}