package tn.elearning.services;

import tn.elearning.entities.ArticleProgress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleProgressService {
    
    private static final int POINTS_PER_MINUTE = 2;
    private static final int POINTS_PER_COMMENT = 5;
    private static final int POINTS_FOR_COMPLETION = 20;
    
    // Badges constants
    private static final String QUICK_LEARNER_BADGE = "quick_learner";
    private static final String ACTIVE_READER_BADGE = "active_reader";
    private static final String SUPER_CONTRIBUTOR_BADGE = "super_contributor";

    public void updateProgress(Long userId, Long articleId, int timeSpent) {
        ArticleProgress progress = getProgress(userId, articleId);
        if (progress == null) {
            progress = new ArticleProgress(userId, articleId);
        }

        // Mise à jour du temps et calcul des points
        progress.setTimeSpentMinutes(progress.getTimeSpentMinutes() + timeSpent);
        int newPoints = calculatePoints(timeSpent);
        progress.setInteractionPoints(progress.getInteractionPoints() + newPoints);
        
        // Vérification des badges
        checkAndAwardBadges(progress);
        
        // Sauvegarde
        saveProgress(progress);
    }

    public void markAsCompleted(Long userId, Long articleId) {
        ArticleProgress progress = getProgress(userId, articleId);
        if (progress != null) {
            progress.setCompleted(true);
            progress.setInteractionPoints(progress.getInteractionPoints() + POINTS_FOR_COMPLETION);
            saveProgress(progress);
        }
    }

    public List<String> getRecommendedArticles(Long userId) {
        List<String> recommendations = new ArrayList<>();
        // Logique de recommandation basée sur :
        // 1. Niveau de l'utilisateur
        // 2. Articles similaires complétés
        // 3. Temps passé sur des sujets similaires
        return recommendations;
    }

    private int calculatePoints(int timeSpent) {
        return timeSpent * POINTS_PER_MINUTE;
    }

    private void checkAndAwardBadges(ArticleProgress progress) {
        List<String> currentBadges = new ArrayList<>();
        if (!progress.getEarnedBadges().isEmpty()) {
            currentBadges.addAll(List.of(progress.getEarnedBadges().split(",")));
        }

        // Vérification des conditions pour chaque badge
        if (progress.getTimeSpentMinutes() > 60 && !currentBadges.contains(ACTIVE_READER_BADGE)) {
            currentBadges.add(ACTIVE_READER_BADGE);
        }
        if (progress.getInteractionPoints() > 100 && !currentBadges.contains(SUPER_CONTRIBUTOR_BADGE)) {
            currentBadges.add(SUPER_CONTRIBUTOR_BADGE);
        }

        progress.setEarnedBadges(String.join(",", currentBadges));
    }

    private ArticleProgress getProgress(Long userId, Long articleId) {
        // TODO: Implémenter la récupération depuis la base de données
        return null;
    }

    private void saveProgress(ArticleProgress progress) {
        // TODO: Implémenter la sauvegarde dans la base de données
    }

    public List<ArticleProgress> getUserProgress(Long userId) {
        // TODO: Implémenter la récupération de toute la progression d'un utilisateur
        return new ArrayList<>();
    }
} 