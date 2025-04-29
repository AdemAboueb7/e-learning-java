package tn.elearning.controller;

public class TestEmail {
    public static void main(String[] args) {
        // 1. Mettez VOTRE email de test ici
        String testEmail = "aziz.bellagha@gmail.com"; // ⚠️ Remplacez par un vrai email que vous possédez

        // 2. Un lien bidon pour le test
        String fakeLink = "http://localhost/test";

        // 3. Appel de la méthode d'envoi
        System.out.println("🚀 Début du test...");
        EmailSender.sendResetPasswordEmail(testEmail, fakeLink);
        System.out.println("✅ Test terminé !");
    }
}