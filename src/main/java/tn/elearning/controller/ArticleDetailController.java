package tn.elearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.elearning.entities.Article;
import tn.elearning.entities.Comment;
import tn.elearning.entities.User;
import tn.elearning.services.ArticleService;
import tn.elearning.services.CommentService;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.application.Platform;
import tn.elearning.utils.NavigationUtil;
import tn.elearning.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ArticleDetailController implements Initializable {

    private final ArticleService articleService = new ArticleService();
    private final CommentService commentService = new CommentService();
    private Article currentArticle;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
    private final DateTimeFormatter commentFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy 'à' HH:mm", Locale.FRENCH);

    @FXML
    private Label titleLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private FlowPane relatedArticlesContainer;
    
    @FXML
    private Label noRelatedLabel;
    
    @FXML
    private VBox commentsContainer;
    
    @FXML
    private TextArea newCommentArea;
    
    @FXML
    private Button submitCommentButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialiser les articles
            List<Article> articles = articleService.recuperer();
            
            // Configuration des boutons d'action selon le rôle
            User currentUser = UserSession.getInstance().getUser();
            boolean isAdmin = currentUser != null && currentUser.getRoles() != null && 
                            currentUser.getRoles().stream().anyMatch(role -> role.equals("ROLE_ADMIN"));
            
            // Afficher les boutons Modifier et Supprimer pour l'admin
            if (editButton != null) {
                editButton.setVisible(isAdmin);
                editButton.setManaged(isAdmin);
                editButton.setOnAction(this::openEditForm);
            }
            
            if (deleteButton != null) {
                deleteButton.setVisible(isAdmin);
                deleteButton.setManaged(isAdmin);
                deleteButton.setOnAction(this::deleteArticle);
            }
            
            if (submitCommentButton != null) {
                submitCommentButton.setOnAction(this::addComment);
            }
            
            // Configuration de la zone de commentaire
            if (newCommentArea != null) {
                newCommentArea.setWrapText(true);
                newCommentArea.setPromptText("Écrivez votre commentaire ici...");
            }
            
            // Initialiser les conteneurs
            if (commentsContainer != null) {
                commentsContainer.setSpacing(10);
            }
            
            if (relatedArticlesContainer != null) {
                relatedArticlesContainer.setHgap(10);
                relatedArticlesContainer.setVgap(10);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur d'Initialisation", "Impossible de charger les articles : " + e.getMessage());
        }
    }

    public void setArticle(Article article) {
        this.currentArticle = article;
        displayArticleDetails();
        loadRelatedArticles();
        loadComments();
    }

    private void displayArticleDetails() {
        if (currentArticle == null) return;

        titleLabel.setText(currentArticle.getTitle());
        categoryLabel.setText(currentArticle.getCategory());
        contentLabel.setText(currentArticle.getContent());

        if (currentArticle.getCreatedAt() != null) {
            dateLabel.setText("Publié le : " + currentArticle.getCreatedAt().format(formatter));
        } else {
            dateLabel.setText("Date de publication indisponible");
        }
    }

    private void loadRelatedArticles() {
        relatedArticlesContainer.getChildren().clear();
        noRelatedLabel.setVisible(false);
        noRelatedLabel.setManaged(false);
        
        try {
            if (currentArticle == null || currentArticle.getCategory() == null) {
                 showNoRelatedArticles();
                 return;
            }

            List<Article> allArticles = articleService.recuperer();
            
            List<Article> relatedArticles = allArticles.stream()
                    .filter(article -> article != null &&
                            article.getCategory() != null &&
                            article.getCategory().equals(currentArticle.getCategory()) &&
                            article.getId() != currentArticle.getId())
                    .limit(3)
                    .collect(Collectors.toList());
            
            if (relatedArticles.isEmpty()) {
                showNoRelatedArticles();
            } else {
                displayRelatedArticles(relatedArticles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Impossible de charger les articles similaires : " + e.getMessage());
             showNoRelatedArticles();
        }
    }
    
    private void loadComments() {
        try {
             if (currentArticle == null) return;

            List<Comment> allComments = commentService.recuperer();
            
            List<Comment> articleComments = allComments.stream()
                    .filter(comment -> comment != null && comment.getArticle() != null && 
                            comment.getArticle().getId() == currentArticle.getId())
                    .sorted(Comparator.comparing(Comment::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
            
            displayComments(articleComments);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Impossible de charger les commentaires : " + e.getMessage());
        }
    }
    
    private void displayComments(List<Comment> comments) {
        commentsContainer.getChildren().clear();
        
        if (comments.isEmpty()) {
            Label noCommentsLabel = new Label("Aucun commentaire pour le moment. Soyez le premier !");
            noCommentsLabel.getStyleClass().add("text-muted");
            commentsContainer.getChildren().add(noCommentsLabel);
            return;
        }
        
        User currentUser = UserSession.getInstance().getUser();
        boolean isAdmin = currentUser != null && currentUser.getRoles() != null && 
                         currentUser.getRoles().stream().anyMatch(role -> role.equals("ROLE_ADMIN"));
        
        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentCard.fxml"));
                VBox commentCard = loader.load();
                
                Label userLabel = (Label) commentCard.lookup("#userLabel");
                Label dateLabel = (Label) commentCard.lookup("#dateLabel");
                Label contentLabel = (Label) commentCard.lookup("#contentLabel");
                Button editButton = (Button) commentCard.lookup("#editCommentButton");
                Button deleteButton = (Button) commentCard.lookup("#deleteCommentButton");
                
                if(userLabel != null) userLabel.setText("Utilisateur #" + comment.getUserId());
                
                if (dateLabel != null) {
                     if (comment.getCreatedAt() != null) {
                        dateLabel.setText(comment.getCreatedAt().format(commentFormatter));
                    } else {
                        dateLabel.setText("Publié récemment");
                    }
                }
                
                if (contentLabel != null) contentLabel.setText(comment.getContenu());

                // Vérifier si l'utilisateur est admin ou l'auteur du commentaire
                boolean isCommentOwner = currentUser != null && currentUser.getId() == comment.getUserId();
                boolean canManageComment = isAdmin || isCommentOwner;

                // Afficher/masquer les boutons d'édition et de suppression
                if (editButton != null) {
                    editButton.setVisible(canManageComment);
                    editButton.setManaged(canManageComment);
                    editButton.setOnAction(event -> showCommentEditState(comment, commentCard));
                }

                if (deleteButton != null) {
                    deleteButton.setVisible(canManageComment);
                    deleteButton.setManaged(canManageComment);
                    deleteButton.setOnAction(event -> deleteComment(comment));
                }
                
                commentsContainer.getChildren().add(commentCard);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur", "Impossible de charger la carte de commentaire : " + e.getMessage());
            }
        }
    }

    private void displayRelatedArticles(List<Article> articles) {
        for (Article article : articles) {
            try {
                VBox miniCard = createMiniArticleCard(article);
                 relatedArticlesContainer.getChildren().add(miniCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
     private void showNoRelatedArticles() {
        noRelatedLabel.setVisible(true);
        noRelatedLabel.setManaged(true);
    }

    private VBox createMiniArticleCard(Article article) throws IOException {
        VBox card = new VBox(5);
        card.getStyleClass().add("article-card");
        card.setPrefWidth(220);
        card.setMinWidth(220);
        card.setMaxWidth(220);
        card.setCursor(Cursor.HAND);
        card.setOnMouseClicked(event -> {
            try {
                NavigationUtil.navigateToArticleDetail(article);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur de Navigation", "Impossible d'ouvrir les détails de l'article sélectionné.");
            }
        });
        
        Label titleLabel = new Label(article.getTitle());
        titleLabel.getStyleClass().add("card-title");
        titleLabel.setWrapText(true);
        titleLabel.setMaxHeight(40);
        
        Label categoryLabel = new Label(article.getCategory());
        categoryLabel.getStyleClass().add("card-category");
        
        card.getChildren().addAll(titleLabel, categoryLabel);
        
        return card;
    }
    
    @FXML
    private void addComment(ActionEvent event) {
        if (currentArticle == null) return;
        
        // Vérifier si l'utilisateur est connecté
        if (UserSession.getInstance().getUser() == null) {
            showAlert(AlertType.WARNING, "Non connecté", "Vous devez être connecté pour ajouter un commentaire.");
            return;
        }
        
        String commentContent = newCommentArea.getText().trim();
        
        // Validation du commentaire
        StringBuilder errors = new StringBuilder();
        
        if (commentContent.isEmpty()) {
            errors.append("Le commentaire ne peut pas être vide.\n");
        } else {
            if (commentContent.length() < 2) {
                errors.append("Le commentaire doit contenir au moins 2 caractères.\n");
            }
            if (commentContent.length() > 1000) {
                errors.append("Le commentaire ne doit pas dépasser 1000 caractères.\n");
            }
            if (commentContent.replaceAll("\\s+", "").isEmpty()) {
                errors.append("Le commentaire doit contenir du texte.\n");
            }
        }
        
        if (errors.length() > 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de Validation");
            alert.setHeaderText("Veuillez corriger les erreurs suivantes :");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return;
        }
        
        try {
            Comment comment = new Comment();
            comment.setContenu(commentContent);
            comment.setArticle(currentArticle);
            comment.setUserId(UserSession.getInstance().getUser().getId());
            
            commentService.ajouter(comment);
            
            newCommentArea.clear();
            loadComments();
            
            showAlert(AlertType.INFORMATION, "Succès", "Votre commentaire a été ajouté avec succès !");
            
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Impossible d'ajouter le commentaire : " + e.getMessage());
        }
    }
    
    @FXML
    void goBack(ActionEvent event) {
        try {
            NavigationUtil.navigateToViewArticles();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de Navigation", "Impossible de retourner à la liste des articles.");
        }
    }
    
    private void ensureStageIsSet() {
        if (NavigationUtil.getMainStage() == null && titleLabel != null && titleLabel.getScene() != null) {
            Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            if (currentStage != null) {
                NavigationUtil.setMainStage(currentStage);
            }
        }
    }
    
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void openEditForm(ActionEvent event) {
        if (currentArticle == null) return;
        try {
            ensureStageIsSet();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleEdit.fxml"));
            Parent root = loader.load();

            ArticleEditController controller = loader.getController();
            controller.setArticle(this.currentArticle);

            // Navigate by setting the scene on the main stage
            Stage mainStage = NavigationUtil.getMainStage();
            if (mainStage != null) {
                 // Set the scene, adjusting size if needed
                 // Consider storing original size or using root's pref size
                mainStage.setScene(new Scene(root)); 
                // Optional: Reset title if necessary
                mainStage.setTitle("Modifier l'Article"); 
            } else {
                 System.err.println("Main stage not found in NavigationUtil for editing.");
                 showAlert(AlertType.ERROR, "Erreur Interne", "Impossible d'ouvrir le formulaire de modification.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Impossible de charger le formulaire de modification."); // Adjusted message
        }
    }

    @FXML
    private void deleteArticle(ActionEvent event) {
        if (currentArticle == null) return;
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmer la Suppression");
        confirmation.setHeaderText("Supprimer l'Article");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet article ? Les commentaires associés pourraient aussi être supprimés. Cette action est irréversible.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                articleService.supprimer(currentArticle);
                showAlert(AlertType.INFORMATION, "Succès", "Article supprimé avec succès.");
                goBack(null);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur de Suppression", "Impossible de supprimer l'article : " + e.getMessage());
            }
        }
    }

    private void showCommentEditState(Comment comment, VBox commentCard) {
        Node contentNode = commentCard.lookup("#contentLabel");
        Node actionsNode = commentCard.lookup("#editCommentButton") != null ? 
                         commentCard.lookup("#editCommentButton").getParent() : null;

        if (!(contentNode instanceof Label) || !(actionsNode instanceof HBox)) {
            System.err.println("Could not find comment elements for inline edit.");
            return;
        }
        Label contentLabel = (Label) contentNode;
        HBox originalActionsBox = (HBox) actionsNode;

        contentLabel.setVisible(false);
        contentLabel.setManaged(false);
        originalActionsBox.setVisible(false);
        originalActionsBox.setManaged(false);

        TextArea editTextArea = new TextArea(comment.getContenu());
        editTextArea.setWrapText(true);
        editTextArea.setPrefRowCount(3);
        editTextArea.setId("inlineCommentEditArea");

        Button saveButton = new Button("Enregistrer");
        saveButton.getStyleClass().add("button");
        Button cancelButton = new Button("Annuler");
        cancelButton.getStyleClass().add("button-secondary");

        HBox editActionsBox = new HBox(10, saveButton, cancelButton);
        editActionsBox.setAlignment(Pos.CENTER_RIGHT);
        editActionsBox.setId("inlineCommentEditActions");
        VBox.setMargin(editActionsBox, new javafx.geometry.Insets(10, 0, 0, 0));

        int originalActionsIndex = commentCard.getChildren().indexOf(originalActionsBox);
        if (originalActionsIndex != -1) {
             int contentLabelIndex = commentCard.getChildren().indexOf(contentLabel);
             if (contentLabelIndex != -1) {
                 commentCard.getChildren().add(contentLabelIndex + 1, editTextArea);
                 commentCard.getChildren().add(contentLabelIndex + 2, editActionsBox); 
             } else {
                 commentCard.getChildren().add(originalActionsIndex, editTextArea);
                 commentCard.getChildren().add(originalActionsIndex + 1, editActionsBox); 
             }
        } else {
            commentCard.getChildren().addAll(editTextArea, editActionsBox);
        }
        
        Platform.runLater(() -> editTextArea.requestFocus());
        
        saveButton.setOnAction(event -> {
            String newContent = editTextArea.getText().trim();
            if (newContent.isEmpty()) {
                showAlert(AlertType.WARNING, "Contenu Vide", "Le commentaire ne peut pas être vide.");
                return;
            }
            try {
                comment.setContenu(newContent);
                commentService.modifier(comment);
                showCommentDisplayState(commentCard, comment);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur", "Impossible de modifier le commentaire : " + e.getMessage());
            }
        });

        cancelButton.setOnAction(event -> {
            showCommentDisplayState(commentCard, comment);
        });
    }

    private void showCommentDisplayState(VBox commentCard, Comment comment) {
        Node editAreaNode = commentCard.lookup("#inlineCommentEditArea");
        Node editActionsNode = commentCard.lookup("#inlineCommentEditActions");

        if (editAreaNode != null) commentCard.getChildren().remove(editAreaNode);
        if (editActionsNode != null) commentCard.getChildren().remove(editActionsNode);

        Node contentNode = commentCard.lookup("#contentLabel");
        Node actionsNode = commentCard.lookup("#editCommentButton") != null ? 
                         commentCard.lookup("#editCommentButton").getParent() : null;

        if (contentNode instanceof Label) {
            ((Label) contentNode).setText(comment.getContenu());
            contentNode.setVisible(true);
            contentNode.setManaged(true);
        }
        if (actionsNode instanceof HBox) {
            actionsNode.setVisible(true);
            actionsNode.setManaged(true);
        }
    }

    private void deleteComment(Comment comment) {
        User currentUser = UserSession.getInstance().getUser();
        boolean isAdmin = currentUser != null && currentUser.getRoles() != null && 
                         currentUser.getRoles().stream().anyMatch(role -> role.equals("ROLE_ADMIN"));
        boolean isCommentOwner = currentUser != null && currentUser.getId() == comment.getUserId();

        // Vérifier si l'utilisateur est autorisé à supprimer le commentaire
        if (!isAdmin && !isCommentOwner) {
            showAlert(AlertType.ERROR, "Non autorisé", "Vous ne pouvez supprimer que vos propres commentaires.");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmer la Suppression");
        confirmation.setHeaderText("Supprimer le Commentaire");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce commentaire ? Cette action est irréversible.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                commentService.supprimer(comment);
                loadComments();
                showAlert(AlertType.INFORMATION, "Succès", "Commentaire supprimé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur", "Impossible de supprimer le commentaire : " + e.getMessage());
            }
        }
    }
} 