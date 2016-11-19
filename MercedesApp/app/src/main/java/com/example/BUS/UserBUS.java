package com.example.BUS;

import android.content.Context;

import com.example.DAO.MercedesDB;
import com.example.DTO.User;

public class UserBUS {
    private Context context;

    public UserBUS(Context context) {
        this.context = context;
    }

    public boolean addUserData(User user) {
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        user.setName(user.getName().trim());
        user.setDob(user.getDob().trim());
        user.setEmail(user.getEmail().trim());
        user.setPhone(user.getPhone().trim());

        return new MercedesDB(context).addUserData(user);
    }

    public boolean checkLogin(String username, String password) {
        boolean isContain = false;
        int row = new MercedesDB(context).getUserDataForLoginCheck(username, password);

        if (row > 0) {
            isContain = true;
        }

        return isContain;
    }
}