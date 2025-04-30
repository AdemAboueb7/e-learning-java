package tn.elearning.entities;

import java.time.LocalDateTime;

public class ArticleProgress {
    private Long id;
    private Long userId;
    private Long articleId;
    private int readingScore;
    private int interactionPoints;
    private boolean isCompleted;
    private LocalDateTime lastReadDate;
    private int timeSpentMinutes;
    private String earnedBadges;

    // Constructeurs
    public ArticleProgress() {}

    public ArticleProgress(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
        this.readingScore = 0;
        this.interactionPoints = 0;
        this.isCompleted = false;
        this.lastReadDate = LocalDateTime.now();
        this.timeSpentMinutes = 0;
        this.earnedBadges = "";
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    
    public int getReadingScore() { return readingScore; }
    public void setReadingScore(int readingScore) { this.readingScore = readingScore; }
    
    public int getInteractionPoints() { return interactionPoints; }
    public void setInteractionPoints(int interactionPoints) { this.interactionPoints = interactionPoints; }
    
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    
    public LocalDateTime getLastReadDate() { return lastReadDate; }
    public void setLastReadDate(LocalDateTime lastReadDate) { this.lastReadDate = lastReadDate; }
    
    public int getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(int timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
    
    public String getEarnedBadges() { return earnedBadges; }
    public void setEarnedBadges(String earnedBadges) { this.earnedBadges = earnedBadges; }
} 