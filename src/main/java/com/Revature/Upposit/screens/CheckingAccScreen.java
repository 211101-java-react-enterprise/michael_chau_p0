package com.Revature.Upposit.screens;

import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class CheckingAccScreen extends Screen{
    private final AccountService accountService;

    public CheckingAccScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService) {
        super("CheckingAccScreen", "/checking_accounts", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("        List of your Checking Accounts");
        accountService.displayListOfAcc(1);
    }
}
