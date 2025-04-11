package tn.elearning.tests;

import tn.elearning.entities.Article;
import tn.elearning.entities.Comment;
import tn.elearning.services.ArticleService;
import tn.elearning.services.CommentService;
import tn.elearning.tools.MyDataBase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class main {
    public static void main(String[] args) {
        // Initialisation de la connexion à la base de données
        MyDataBase db = MyDataBase.getInstance();

        // Création des services
        ArticleService articleService = new ArticleService();
        CommentService commentService = new CommentService();

        try {
            // Exemple 1 : Article sur l'histoire de l'art
            Article article1 = new Article();
            article1.setTitle("Les grands mouvements artistiques du 20ème siècle");
            article1.setContent("Le 20ème siècle a été marqué par une explosion de créativité artistique, avec des mouvements comme le cubisme, le surréalisme et l'expressionnisme abstrait qui ont révolutionné notre façon de voir l'art...");
            article1.setCategory("art");
            article1.setUserId(1);
            article1.setImage("art_history.jpg");
            article1.setCreatedAt(LocalDateTime.now());
            articleService.ajouter(article1);
            System.out.println("Article sur l'histoire de l'art ajouté avec succès !");

            // Exemple 2 : Article sur la biologie
            Article article2 = new Article();
            article2.setTitle("La photosynthèse : le processus vital des plantes");
            article2.setContent("La photosynthèse est le processus par lequel les plantes convertissent la lumière solaire en énergie chimique. Ce processus complexe implique plusieurs étapes et est essentiel à la vie sur Terre...");
            article2.setCategory("biologie");
            article2.setUserId(2);
            article2.setImage("photosynthesis.png");
            article2.setCreatedAt(LocalDateTime.now());
            articleService.ajouter(article2);
            System.out.println("Article sur la biologie ajouté avec succès !");

            // Exemple 3 : Article sur la littérature
            Article article3 = new Article();
            article3.setTitle("Les chefs-d'œuvre de la littérature française");
            article3.setContent("La littérature française a produit certains des plus grands chefs-d'œuvre de l'histoire. Des œuvres comme 'Les Misérables' de Victor Hugo, 'Madame Bovary' de Gustave Flaubert, et 'À la recherche du temps perdu' de Marcel Proust ont marqué la culture mondiale...");
            article3.setCategory("litterature");
            article3.setUserId(3);
            article3.setImage("french_literature.jpg");
            article3.setCreatedAt(LocalDateTime.now());
            articleService.ajouter(article3);
            System.out.println("Article sur la littérature ajouté avec succès !");

            // Affichage de tous les articles pour vérification
            System.out.println("\nListe de tous les articles :");
            for (Article article : articleService.recuperer()) {
                System.out.println("-----------------------------------");
                System.out.println("ID: " + article.getId());
                System.out.println("Titre: " + article.getTitle());
                System.out.println("Catégorie: " + article.getCategory());
                System.out.println("Auteur ID: " + article.getUserId());
                System.out.println("Date création: " + article.getCreatedAt());
                System.out.println("-----------------------------------\n");
            }

            // Récupération d'un article existant
            Article article = articleService.getById(1);

            // Création d'un nouveau commentaire
            Comment comment1 = new Comment();
            comment1.setArticle(article);
            comment1.setUserId(2);
            comment1.setContenu("Très instructif ! J'ai beaucoup appris sur l'histoire de l'art.");
            comment1.setCreatedAt(LocalDateTime.now());
            commentService.ajouter(comment1);

            // Création d'un deuxième commentaire
            Comment comment2 = new Comment();
            comment2.setArticle(article);
            comment2.setUserId(3);
            comment2.setContenu("J'aurais aimé plus de détails sur le cubisme.");
            comment2.setCreatedAt(LocalDateTime.now());
            commentService.ajouter(comment2);

            // Affichage de tous les commentaires de l'article
            System.out.println("\nCommentaires de l'article " + article.getTitle() + " :");
            List<Comment> comments = commentService.recuperer();
            for (Comment c : comments) {
                if (c.getArticle().getId() == article.getId()) {
                    System.out.println("-----------------------------------");
                    System.out.println("ID: " + c.getId());
                    System.out.println("Utilisateur ID: " + c.getUserId());
                    System.out.println("Contenu: " + c.getContenu());
                    System.out.println("Date: " + c.getCreatedAt());
                    System.out.println("-----------------------------------\n");
                }
            }

            // Test de la méthode modifier
            System.out.println("\nModification du titre de l'article 1 :");
            articleService.modifier(1, "Les grands mouvements artistiques du 20ème siècle - Édition révisée");

            // Vérification de la modification
            Article articleModifie = articleService.getById(1);
            System.out.println("Nouveau titre : " + articleModifie.getTitle());

            // Test de la méthode modifier pour les commentaires
            System.out.println("\nModification du contenu du premier commentaire :");
            commentService.modifier(1, "Très instructif ! J'ai beaucoup appris sur l'histoire de l'art. Merci pour cet article passionnant.");

            // Vérification de la modification du commentaire
            Comment commentModifie = commentService.getById(1);
            System.out.println("Nouveau contenu : " + commentModifie.getContenu());

            // Test de la méthode supprimer
            System.out.println("\nSuppression du deuxième commentaire :");
            commentService.supprimer(comment2);

            // Vérification de la suppression
            System.out.println("Liste des commentaires après suppression :");
            for (Comment c : commentService.recuperer()) {
                System.out.println("-----------------------------------");
                System.out.println("ID: " + c.getId());
                System.out.println("Utilisateur ID: " + c.getUserId());
                System.out.println("Contenu: " + c.getContenu());
                System.out.println("Date: " + c.getCreatedAt());
                System.out.println("-----------------------------------\n");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'opération sur la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
