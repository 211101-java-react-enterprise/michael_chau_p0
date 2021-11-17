package com.Revature.Upposit.util;


import com.Revature.Upposit.daos.AppUserDAO;
import com.Revature.Upposit.screens.LoginScreen;
import com.Revature.Upposit.screens.RegisterScreen;
import com.Revature.Upposit.screens.WelcomeScreen;

import com.Revature.Upposit.daos.AccountDAO;
import com.Revature.Upposit.screens.*;
import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        AccountDAO accDao = new AccountDAO();
        AccountService accountService = new AccountService(accDao, userService);

        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader, router, userService));
        router.addScreen(new LoginScreen(consoleReader, router, userService));
        router.addScreen(new DashboardScreen(consoleReader, router, userService));
        router.addScreen(new AccCreationScreen(consoleReader, router, userService, accountService));
        router.addScreen(new UppositScreen(consoleReader, router, userService, accountService));
        router.addScreen(new WithdrawalScreen(consoleReader, router, userService, accountService));
        router.addScreen(new CheckingAccScreen(consoleReader, router, accountService));
        router.addScreen(new SavingsAccScreen(consoleReader, router, accountService));

    }

    public void startup() {

        try {
            while (appRunning) {
                router.navigate("/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}
