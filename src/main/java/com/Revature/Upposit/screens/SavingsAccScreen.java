package com.Revature.Upposit.screens;

import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class SavingsAccScreen extends Screen{
    private final AccountService accountService;

    public SavingsAccScreen(BufferedReader consoleReader, ScreenRouter router, AccountService accountService) {
        super("SavingsAccScreen", "/savings_accounts", consoleReader, router);
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("       List of your Savings Accounts");
        accountService.displayListOfAcc(2);
    }
}
