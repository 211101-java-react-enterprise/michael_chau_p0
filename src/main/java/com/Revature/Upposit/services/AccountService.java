package com.Revature.Upposit.services;

import com.Revature.Upposit.daos.AppUserDAO;
import com.Revature.Upposit.exceptions.InvalidRequestException;
import com.Revature.Upposit.exceptions.ResourcePersistenceException;
import com.Revature.Upposit.models.Account;

import com.Revature.Upposit.daos.AccountDAO;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.util.ArrayDeque;

public class AccountService {

    private Account account;
    private UserService sessionUser;
    private AccountDAO accountDao;

    public AccountService(AccountDAO accDAO) {
        this.accountDao = accDAO;
    }

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

    public UserService getUserService() {
        return sessionUser;
    }

    public ArrayDeque<Account> getAccounts(String userId) {
        // Returns a list of accounts based off user id.
        return accountDao.findAccountsByUserId(userId);
    }

    public boolean displayListOfAcc(int type){
        String acc_type = (type==1) ? "Checking" : "Savings";
        ArrayDeque<Account> accountList = getAccounts(getUserService().getUser().getId());
        String result = "\n";

        if (accountList.isEmpty()){
            System.out.println("You currently have no accounts");
            return false;
        }

        int size = accountList.size();
        int runningCount = 1;
        for (int i=0; i< size; i++){
            Account acc = accountList.pollFirst();

            if (acc.getAcc_type().equals(acc_type)){
                result = result + (runningCount) + ") "+acc.getAccName()+" "+acc.getAcc_type()+" Account" +
                        "        Balance: $"+acc.getBalanceToString() + "\n";
                runningCount++;
            }
        }

        System.out.println(result);
        return true;
    }
}
