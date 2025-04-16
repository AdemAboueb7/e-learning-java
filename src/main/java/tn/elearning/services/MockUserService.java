package services;

import entities.User;

public class MockUserService {

    public User authenticate(String username, String password) {
        // Test temporaire
        if (username.equals("admin") && password.equals("admin")) {
            return new User(1, "admin", "admin", "admin");
        } else if (username.equals("user") && password.equals("user")) {
            return new User(2, "user", "user", "user");
        }
        return null;
    }
}
