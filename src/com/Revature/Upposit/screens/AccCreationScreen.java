package com.Revature.Upposit.screens;

import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class AccCreationScreen extends Screen {

    private final UserService userService;
    private final AccountService accountService;

    public AccCreationScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("AccountCreationScreen", "/create", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getUser();

        System.out.println("       Account Creation Menu");
        System.out.print("What type of account do you want to create?\n" +
                "1) Checking\n" +
                "2) Saving\n" +
                "3) Exit\n> ");
        String acc_type = consoleReader.readLine();

        if (acc_type.equals("1")) {
            acc_type = "checking";
        } else if (acc_type.equals("2")) {
            acc_type = "savings";
        } else router.navigate("/dashboard");

        System.out.print("Initial upposit: ");
        String upposit = consoleReader.readLine();

        System.out.println("Thank you, please allow me to confirm your upposit details.");

        try {
            boolean creationIsSuccessful = accountService.createNewAcc(sessionUser, acc_type, upposit);
            if(creationIsSuccessful)System.out.println("Successfully created your " + acc_type + " account.");

        } catch (InvalidRequestException e) {
            System.out.println("You have provided invalid data. Please try again.");
        } catch (ResourcePersistenceException e) {
            System.out.println("There was an issue when trying to persist the user to the datasource.");
        }
    }
}
