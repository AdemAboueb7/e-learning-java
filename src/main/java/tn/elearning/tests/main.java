package tn.elearning.tests;

import tn.elearning.entities.quizz;
import tn.elearning.services.ServiceQuizz;
import tn.elearning.tools.MyDataBase;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Ensure DB connection is initiated
        MyDataBase db = MyDataBase.getInstance();

        System.out.println("Bienvenue dans l'application Quizz 📚");
        runTerminalInterface();
    }

    public static void runTerminalInterface() {
        Scanner scanner = new Scanner(System.in);
        ServiceQuizz service = new ServiceQuizz();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Ajouter un quizz");
            System.out.println("2. Afficher tous les quizz");
            System.out.println("3. Modifier un quizz");
            System.out.println("4. Supprimer un quizz");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    scanner.nextLine(); // consume newline
                    System.out.print("Matière: ");
                    String matiere = scanner.nextLine();
                    System.out.print("Chapitre: ");
                    int chapitre = scanner.nextInt();
                    System.out.print("Difficulté (1-5): ");
                    int difficulte = scanner.nextInt();
                    quizz q = new quizz(matiere, chapitre, difficulte);
                    service.ajouter(q);
                }
                case 2 -> {
                    List<quizz> list = service.recuperer();
                    list.forEach(q -> System.out.println(q.getId() + " - " + q.getMatiere() + " | Chapitre: " + q.getChapitre()));
                }
                case 3 -> {
                    System.out.print("ID du quizz à modifier: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Nouvelle matière: ");
                    String nom = scanner.nextLine();
                    service.modifier(id, nom);
                }
                case 4 -> {
                    System.out.print("ID du quizz à supprimer: ");
                    int id = scanner.nextInt();
                    quizz q = new quizz();
                    q.setId(id);
                    service.supprimer(q);
                }
                case 0 -> {
                    System.out.println("👋 Au revoir !");
                    return;
                }
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }
}
