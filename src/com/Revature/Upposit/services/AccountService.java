package com.Revature.Upposit.services;

import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.Account;

import com.Revature.Upposit.daos.AccountDAO;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.util.ArrayDeque;
import com.Revature.Upposit.util.List;

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
        } catch (InvalidRequestException e) {
            return false;
        }

        if(num < 0) {
            return false;
        }
        return true;
    }

    public boolean withdrawFromAcc(Account account, String value) {
        if (!isBalanceValid(value)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        double value_dbl = Double.parseDouble(value);
        if(value_dbl > account.getBalance()) {
            throw new InvalidRequestException("You cannot withdraw more than the account balance.");
        }
        account.setBalance(account.getBalance() - value_dbl);
        return accountDao.update(account);
    }

    public boolean uppositIntoAcc(Account account, String value) {
        if (!isBalanceValid(value)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }
        double dbl_value = Double.parseDouble(value);

        account.setBalance(account.getBalance() + dbl_value);
        return accountDao.update(account);
    }

//    public UserService getUser() {
//        return sessionUser;
//    }

    public ArrayDeque<Account> getAccounts(String userId) {
        // Returns a list of accounts based off user id.
        return accountDao.findAccountsByUserId(userId);
    }

    public boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
