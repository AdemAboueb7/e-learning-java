package tn.elearning.utils;

import tn.elearning.entities.User;

public class UserSession {
    private static UserSession instance;
    private User user;

    private UserSession() {}
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public boolean isUserLoggedIn() {
        return user != null;
    }
}

