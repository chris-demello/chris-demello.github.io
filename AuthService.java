/*
 * Part of planned enhancement: Category 1 - Software Design: Refactoring service layer
 * Christopher DeMello
 * Professor Conlan
 * CS499
 * Created: 1/21/26
 */
package com.CS360.weighttracker.service;

import android.database.sqlite.SQLiteConstraintException;

import com.CS360.weighttracker.Dao.UserDAO;
import com.CS360.weighttracker.model.User;
import com.CS360.weighttracker.util.PasswordUtils;

import java.util.Locale;

//This is a service layer component for user authentication and registration. It centralizes all login
// and account creation logic. It acts as a middle man between the UI layer and the database
public class AuthService {
    private final UserDAO userDao;

    public AuthService(UserDAO userDao) {
        this.userDao = userDao;
    }

    //verifies user when signing in
    public User authenticate(String username, String password) {
        //Checks for null pointer exception
        if (username == null || password == null)
            return null;
        //gets user by matching username
        User user = userDao.getUserByUsername(username);
        //if user doesn't exist
        if (user == null) {
            return null;
        }
        //hashes the input password and compares it to stored hash
        boolean ok = PasswordUtils.verifyPassword(
                password,
                user.getPasswordHash(),
                user.getPasswordSalt(),
                user.getPasswordIters()
        );
        //returns authenicated user if valid
        return ok ? user : null;
    }
    // Inserts a new user into database and hashes password
    public boolean register(String username, String password) {
        //check if null
        if (username == null || password == null) return false;
        //trims whitespace
        username = username.trim();
        //checks if empty
        if (username.isEmpty() || password.isEmpty()) return false;
        //checks password length
        if (password.length() < 6) return false;
        //Generates a salt
        String salt = PasswordUtils.generateSalt();
        //gets iteration
        int iters = PasswordUtils.ITERS;
        //hashes password
        String hash = PasswordUtils.hashPassword(password, salt, iters);
        //makes a new user with username, hash, salt, and iterations
        User user = new User(username, hash, salt, iters);
        //adds user to database
        try {
            userDao.insert(user);
            return true;
            // Unique username violated, throws exception
        } catch (SQLiteConstraintException dup) {
            return false;
        }
    }
}
