package tn.elearning.utils;

import tn.elearning.entities.User;

public class UserSession {
    // L'utilisateur actuellement connecté
    private static UserSession instance;
    private User user;

    // Constructor privé pour empêcher l'instantiation multiple
    private UserSession() {}

    // Méthode pour obtenir l'instance unique de UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getter pour récupérer l'utilisateur connecté
    public User getUser() {
        return user;
    }

    // Setter pour définir l'utilisateur connecté
    public void setUser(User user) {
        this.user = user;
    }

    // Méthode pour vérifier si un utilisateur est connecté
    public boolean isUserLoggedIn() {
        return user != null;
    }
}

