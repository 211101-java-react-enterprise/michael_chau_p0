package com.Revature.Upposit.screens;

import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

import static com.Revature.Upposit.util.AppState.shutdown;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        System.out.print("Welcome to Upposit!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/register");
                break;
            case "3":
                System.out.println("Exiting application...");
                shutdown();
                break;
            case "throw exception":
                throw new RuntimeException();
            default:
                System.out.println("The user made an invalid selection");
        }

    }
}
