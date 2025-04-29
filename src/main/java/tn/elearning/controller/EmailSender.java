package tn.elearning.controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void sendResetPasswordEmail(String toEmail, String resetLink) {
        // 1. Configuration SMTP
        String host = "smtp.gmail.com"; // Exemple pour Gmail
        String from = "bendhifallahines@gmail.com";
        String password = "sydv wqmg fnmy zhco"; // Mot de passe d'application Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // 2. Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 3. Construction du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Réinitialisation de mot de passe");

            String text = "Cliquez sur ce lien pour réinitialiser votre mot de passe:\n\n"
                    + resetLink + "\n\n"
                    + "Lien valable 24 heures.";

            message.setText(text);

            // 4. Envoi
            Transport.send(message);
            System.out.println("Email envoyé avec succès à " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Erreur d'envoi: " + e.getMessage());
            throw new RuntimeException("Échec d'envoi d'email", e);
        }
    }
}

