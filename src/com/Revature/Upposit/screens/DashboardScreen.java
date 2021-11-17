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
            System.out.printf("\n%s's Dashboard\n", sessionUser.getFirstName());

            String menu =
                    "Please select an option:\n" +
                    "1) View Checking Account(s) \n" +
                    "2) View Saving Account(s) \n" +
                    "3) Make an upposit\n" +
                    "4) Make a withdrawal\n" +
                    "5) Transfer between accounts\n" +
                    "6) View/edit profile details\n" +
                    "7) Logout\n" +
                    "8) Create Account\n"        +
                    "> ";

            System.out.print(menu);

            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("View/edit profile selected");
                    break;
                case "2":
                    System.out.println("View/edit study sets selected");
                    break;
                case "3":
                    router.navigate("/accounts");
                    break;
                case "4":
                    break;
                case "5":
                    router.navigate("/accounts");
                    break;
                case "6":
                    router.navigate("/accounts");
                    break;
                case "7":
                    System.out.printf("Logging out. Have a good day, %s.\n", sessionUser.getFirstName());
                    userService.logoutUser();
                    break;
                case "8":
                    router.navigate("/create");
                    break;
                default:
                    System.out.println("You have made an invalid selection");
            }
        }

    }

}
