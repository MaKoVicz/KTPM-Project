package com.example.DTO;

public class CurrentLoginUser {
    private static User currentUser;

    private CurrentLoginUser() {

    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User();
        }

        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
