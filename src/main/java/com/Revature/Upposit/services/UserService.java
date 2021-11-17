package com.Revature.Upposit.services;

import com.Revature.Upposit.daos.AppUserDAO;
import com.Revature.Upposit.exceptions.AuthenticationException;
import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.AppUser;

public class UserService {

    private AppUserDAO userDAO = new AppUserDAO();
    private AppUser loggedInUser;

    public UserService(AppUserDAO userDAO) {
        this.userDAO = userDAO;
        this.loggedInUser = null;
    }

    public boolean registerNewUser(AppUser newUser) {

        if(userDAO.findUserByEmail(newUser.getEmail()) != null) {
            throw new ResourcePersistenceException("A user already used this email.");
        }
        if(userDAO.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("A user already used this username.");
        }

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        AppUser registeredUser = userDAO.save(newUser);

        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return true;

    }

    public AppUser authenticateUser(String username, String password) {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        AppUser authenticatedUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        loginUser(authenticatedUser);

        return authenticatedUser;

    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    public AppUser getUser() {
        return loggedInUser;
    }

    public boolean isUserLoggedIn() {
        return (loggedInUser != null);
    }

    public void logoutUser() {
        loggedInUser = null;
    }

    public void loginUser(AppUser user) {
        loggedInUser = user;
    }
}
