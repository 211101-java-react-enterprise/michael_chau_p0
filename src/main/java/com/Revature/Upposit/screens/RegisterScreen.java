package com.Revature.Upposit.screens;

import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    private final UserService userService;

    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("RegisterScreen", "/register", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("The user selected Register");
        System.out.println("Please provide us with some basic information.");
        System.out.print("First name: ");
        String firstName = consoleReader.readLine();

        System.out.print("Last name: ");
        String lastName = consoleReader.readLine();

        System.out.print("Email: ");
        String email = consoleReader.readLine();

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        System.out.printf("Thank you, %s %s\n", firstName, lastName);

        AppUser newUser = new AppUser(firstName, lastName, email, username, password);

        try {
            userService.registerNewUser(newUser);
            router.navigate("/login");

        } catch (InvalidRequestException e) {
            System.out.println("You have provided invalid data. Please try again.");
        } catch (ResourcePersistenceException e) {
            System.out.println("There was an issue when trying to persist the user to the datasource.");
        }


    }

}
