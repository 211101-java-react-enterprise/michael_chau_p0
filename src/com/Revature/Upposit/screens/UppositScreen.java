package com.Revature.Upposit.screens;

import com.Revature.Upposit.models.Account;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ArrayDeque;
import com.Revature.Upposit.util.ArrayList;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class UppositScreen extends Screen {

    private final UserService userService;
    private final AccountService accountService;

    public UppositScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("UppositScreen", "/upposit", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        AppUser user = userService.getUser();
        ArrayDeque<Account> accountList = accountService.getAccounts(user.getId());
        ArrayList<Account> fullList = new ArrayList<>();
        String options = "";

        if (accountList.isEmpty()){
            System.out.println("You currently have no accounts, "+user.getFirstName());
            router.navigate("/dashboard");
        }

        for (int i=0; i< accountList.size(); i++){
            Account acc = accountList.pollFirst();
            fullList.add(acc);
            options = options + (i+1) + ") "+acc.getAccName()+" "+acc.getAcc_type()+" Account +" +
                    "        Balance: $"+acc.getBalance() + "\n";
        }

        System.out.println("Account Upposit Menu");
        System.out.println("Which account do you want to make an Upposit to?");
        System.out.print(options);
        System.out.println((fullList.size()+1)+") Checking");

        String acc_type = consoleReader.readLine();



    }
}
