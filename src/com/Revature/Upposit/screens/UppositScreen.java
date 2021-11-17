package com.Revature.Upposit.screens;

import com.Revature.Upposit.models.Account;
import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ArrayDeque;
import com.Revature.Upposit.util.List;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public class UppositScreen extends Screen {

    private final UserService userService;
    private final AccountService accountService;

    public UppositScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("AccountCreationScreen", "/upposit", consoleReader, router);
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() throws Exception {
        String sessionUser = userService.getUser().getId();

        ArrayDeque<Account> accountList = accountService.getAccounts(sessionUser);


    }
}
