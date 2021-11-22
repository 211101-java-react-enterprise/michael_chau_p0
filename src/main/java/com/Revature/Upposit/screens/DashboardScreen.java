package com.Revature.Upposit.screens;

import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen{

    private final UserService userService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService){
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
    }


    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getUser();

        if (sessionUser == null) {
            System.out.println("You are not currently logged in! Navigating to Login Screen");
            router.navigate("/login");
            return;
        }

        while (userService.isUserLoggedIn()) {
            System.out.printf("\n       Main Menu: %s's Dashboard\n", sessionUser.getFirstName());

            String menu =
                    "Please select an option:\n" +
                    "1) View Checking Account(s) \n" +
                    "2) View Saving Account(s) \n" +
                    "3) Make an upposit\n" +
                    "4) Make a withdrawal\n" +
                    "5) Create\n" +
                    "6) Logout\n" +
                    "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    router.navigate("/checking_accounts");
                    break;
                case "2":
                    router.navigate("/savings_accounts");
                    break;
                case "3":
                    router.navigate("/upposit");
                    break;
                case "4":
                    router.navigate("/withdrawal");
                    break;
                case "5":
                    router.navigate("/create");
                    break;
                case "6":
                    System.out.printf("Logging out. Have a good day, %s.\n", sessionUser.getFirstName());
                    logger.log("User: %s signed off", sessionUser.getUsername());
                    userService.logoutUser();
                    break;
                default:
                    System.out.println("You have made an invalid selection");
            }
        }

    }

}
