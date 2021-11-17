package com.Revature.Upposit.screens;

import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.models.Account;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.services.AccountService;
import com.Revature.Upposit.services.UserService;
import com.Revature.Upposit.util.ArrayDeque;
import com.Revature.Upposit.util.ArrayList;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;
import java.sql.SQLException;

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
        String options = " ";

        if (accountList.isEmpty()){
            System.out.println("You currently have no accounts, "+user.getFirstName());
            router.navigate("/dashboard");
        }

        int size = accountList.size();
        for (int i=0; i<= size; i++){
            Account acc = accountList.pollFirst();
            fullList.add(acc);
            options = options + (i) + ") "+acc.getAccName()+" "+acc.getAcc_type()+" Account" +
                    "        Balance: $"+acc.getBalance() + "\n";
        }

        System.out.println("\nAccount Upposit Menu\n");
        System.out.println("Which account do you want to make an Upposit to?");
        System.out.print(options);
        System.out.println((size+1)+") Exit");
        String input_acc_index = consoleReader.readLine();
        int index = -1;

        try {
            index = Integer.parseInt("input_acc_index");
        } catch(InvalidRequestException e) {
            System.out.println("You have provided invalid data. Please try again.");
        }

        if (index >= (size+1) || index <= 0) throw new InvalidRequestException("Returning to main menu.");

        System.out.print("How much would you like to Upposit? ");
        String input_val = consoleReader.readLine();

        try {
            Account acc = fullList.get(index);
            boolean updateIsSuccessful = accountService.uppositIntoAcc(acc, input_val);
            if(updateIsSuccessful)System.out.println("Successfully updated your account.");

        }catch (InvalidRequestException e) {
            System.out.println("You have provided invalid data. Please try again.");
        }

    }
}
