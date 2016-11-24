package com.example.BUS;

import android.content.Context;

import com.example.DAO.MercedesDB;
import com.example.DTO.User;

import java.security.MessageDigest;

public class UserBUS {
    private Context context;

    public UserBUS(Context context) {
        this.context = context;
    }

    public boolean addUserData(User user) {
        user.setUsername(user.getUsername().trim().toLowerCase());
        user.setPassword(hashPassword(user.getPassword().trim()));
        user.setName(user.getName().trim());
        user.setDob(user.getDob().trim());
        user.setEmail(user.getEmail().trim());
        user.setPhone(user.getPhone().trim());

        return new MercedesDB(context).addUserData(user);
    }

    public boolean checkEmailExisted(String email) {
        int rowCount = new MercedesDB(context).getUserEmailForSignUpCheck(email);
        if (rowCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkLogin(String username, String password) {
        boolean isContain = false;
        username = username.trim().toLowerCase();
        password = hashPassword(password);
        int row = new MercedesDB(context).getUserDataForLoginCheck(username, password);

        if (row > 0) {
            isContain = true;
        }

        return isContain;
    }

    public String hashPassword(final String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(password.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString();
        } catch (Exception exc) {
            return "";
        }
    }

    public User getUserData(String username) {
        return new MercedesDB(context).getUserData(username);
    }
}