package tn.elearning.utils;

import tn.elearning.entities.User;

public class UserSession {
    private static UserSession instance;
    private User user;
    private String sessionId;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Récupérer l'utilisateur actuellement connecté
    public User getUser() {
        return user;
    }

    // Définir l'utilisateur connecté
    public void setUser(User user) {
        this.user = user;
    }

    // Vérifier si un utilisateur est connecté
    public boolean isUserLoggedIn() {
        return user != null;
    }

    // Définir l'ID de session
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // Récupérer l'ID de session
    public String getSessionId() {
        return sessionId;
    }

    // Effacer l'ID de session
    public void clearSessionId() {
        this.sessionId = null;
    }

    // Effacer toutes les informations de session (utilisateur et ID)
    public void clear() {
        this.user = null;      // Effacer les informations de l'utilisateur
        this.sessionId = null; // Effacer l'ID de session
    }
}
