package com.Revature.Upposit.services;

import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.Account;

import com.Revature.Upposit.daos.AccountDAO;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.util.List;

import java.util.UUID;

public class AccountService {

    private Account account;
    private final UserService sessionUser;
    private final AccountDAO accountDao;

    public AccountService(AccountDAO accountDao, UserService sessionUser) {
        this.accountDao = accountDao;
        this.sessionUser = sessionUser;
    }

    public boolean createNewAcc(AppUser user,String acc_type, String upposit) {

        if (!isBalanceValid(upposit)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        Account newAcc = new Account(user.getId(),acc_type,upposit);

        accountDao.save(newAcc);

        if (newAcc == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return true;

    }

    public boolean isBalanceValid(String balance){
        double num;

        if(balance == null || balance.equals("")) {
            return false;
        }
        try {
            num = Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            return false;
        }

        if(num < 0) {
            return false;
        }
        return true;
    }

    public boolean withdrawMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new InvalidRequestException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isNumeric(value)) {
            throw new InvalidRequestException("Please enter a positive numeric value");
        }
        if(!isPositiveNumber(value)) {
            throw new InvalidRequestException("User is not allowed to enter a negative number");
        }
        double moneyToWithdraw = Double.parseDouble(value);
        if(moneyToWithdraw > account.getBalance()) {
            throw new InvalidRequestException("You are attempting to withdraw more than you have");
        }
        account.setBalance(account.getBalance() - moneyToWithdraw);
        return accountDao.update(account);
    }

    public boolean depositMoney(Account account, String value) {
        if(!isProperFormat(value)) {
            throw new InvalidRequestException("Please enter a numeric value with no more than 2 decimal places");
        }
        if(!isNumeric(value)) {
            throw new InvalidRequestException("Please enter a positive numeric value");
        }

        if(!isPositiveNumber(value)) {
            throw new InvalidRequestException("User is not allowed to enter a negative number");
        }
        double moneyToDeposit = Double.parseDouble(value);

        account.setBalance(account.getBalance() + moneyToDeposit);
        return accountDao.update(account);
    }

    public UserService getSessionUser() {
        return sessionUser;
    }

//    public List<Account> returnMyAccounts() {
//        return (List<Account>) accountDao.findAccountsByUserId(sessionUser.getUser());
//    }

//    public boolean changeToAccount(UUID User_id, String account_id) {
//
//        if(Integer.parseInt(account_id) < 0) {
//            throw new NegativeAccountIdException("Account IDs can't be negative");
//        }
//        account = accountDao.findAccountByUserAndAccountId(User_id, account_id);
//        if(account == null) {
//            throw new UnownedAccountException("User does not own this account");
//        }
//        sessionUser.getSessionUser().setCurrentAccount(account);
//        return true;
//    }

    public boolean isPositiveNumber(String value) {
        double money = Double.parseDouble(value);
        if(money < 0) {
            return false;
        }
        return true;
    }

    public boolean isNumeric(String value) {
        if(value == null || value.equals("")) {
            return false;
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isProperFormat(String value) {
        //checks to see if it is a whole number
        if(Double.parseDouble(value) % 1 == 0) {
            return true;
        }
        int integerLength = value.indexOf(".");
        int decimalLength = value.length() - 1 - integerLength;
        if(decimalLength > 2) {
            return false;
        }
        return true;
    }

//    public boolean createNewAccount(String type) {
//        Account account = null;
//        if(type.equals("savings")) {
//            account = new SavingsAccount();
//        } else if(type.equals("checkings")) {
//            account = new CheckingsAccount();
//        }
//        account.setType(type);
//        account.setUser(sessionUser.getSessionUser());
//
//        return accountDao.save(account) != null;
//    }

//    public List<Transactions> viewAllTransactions(String User_id) {
//        List<Transactions> list = accountDao.viewAllAccountsTransactions(User_id);
//        if(list.isEmpty()) {
//            throw new EmptyTransactionsException("There are no previously existing transactions");
//        }
//        System.out.println("Not empty");
//        return list;
//    }
//    public List<Transactions> viewSingleTransactions(String account_id) {
//        if(!isNumeric(account_id) || !isInteger(account_id)) {
//            throw new InvalidRequestException("This is not an account_id");
//        }
//        if(!isPositiveNumber(account_id)) {
//            throw new InvalidRequestException("An account number cannot be negative");
//        }
//        List<Transactions> list = accountDao.selectTransactionByAccountId(account_id);
//        // TODO figure out how to get this to be null if possible
//        if(list == null) {
//            throw new UnownedAccountException("You do not own this account");
//        }
//        if(list.isEmpty()) {
//            throw new UnownedAccountException("There are no previously existing transactions");
//        }
//        return list;
//    }
//
//    public boolean isInteger(String value) {
//        try {
//            Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            return false;
//        }
//        return true;
//    }
}
