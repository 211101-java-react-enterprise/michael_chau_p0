package com.Revature.Upposit.screens;

import com.Revature.Upposit.exceptions.AuthenticationException;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;
import java.time.LocalDateTime;

public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        System.out.println("Please provide your credentials to log into your account.");
        System.out.print("Username > ");
        String username = consoleReader.readLine();
        System.out.print("Password > ");
        String password = consoleReader.readLine();

        try {
            AppUser authenticatedUser = userService.authenticateUser(username, password);
            logger.log("Successful authentication and login of user: %s", username);
            router.navigate("/dashboard");
        } catch (AuthenticationException e) {
            System.out.println("Incorrect credentials provided! No matching user account found.");
        }

    }

}
