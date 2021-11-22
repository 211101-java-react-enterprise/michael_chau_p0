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

public class WithdrawalScreen extends Screen{

    private final UserService userService;
    private final AccountService accountService;

    public WithdrawalScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService, AccountService accountService) {
        super("WithdrawalScreen", "/withdrawal", consoleReader, router);
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

        int size = accountList.size();
        for (int i=0; i< size; i++){
            Account acc = accountList.pollFirst();
            fullList.add(acc);
            String formattedBal = String.format("%.02f", acc.getBalance());
            options = options + (i+1) + ") "+acc.getAccName()+" "+acc.getAcc_type()+" Account" +
                    "        Balance: $"+formattedBal + "\n";
        }

        System.out.println("\n           Account Withdrawal Menu\n");
        System.out.println("Which account would you like to withdraw from?");
        System.out.print(options);
        System.out.print((size+1)+") Exit\n> ");
        String input_acc_index = consoleReader.readLine();
        int index = -1;

        try {
            index = Integer.parseInt(input_acc_index);
        } catch(NumberFormatException e) {
            System.out.println("You have provided invalid data. Please try again.");
        }

        if ((index>size) || index <= 0) {
            System.out.println("Returning to main menu.");
            router.navigate("/dashboard");
        } else {
            System.out.print("How much would you like to withdraw? ");
            String input_val = consoleReader.readLine();

            try {
                Account acc = fullList.get(index-1);
                boolean updateIsSuccessful = accountService.withdrawFromAcc(acc, input_val);
                if(updateIsSuccessful)System.out.println("Successfully updated your account.");

            }catch (InvalidRequestException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You have provided invalid data. Please try again.");
            }
        }

    }
}
