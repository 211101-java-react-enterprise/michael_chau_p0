package com.Revature.Upposit.services;

import com.Revature.Upposit.daos.AccountDAO;
import com.Revature.Upposit.daos.AppUserDAO;
import com.Revature.Upposit.models.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AccountServiceTestSuite {
    AccountService sut;
    AccountDAO mockAccDAO;

    @Before
    public void testCaseSetup() {
        mockAccDAO = mock(AccountDAO.class);
        sut = new AccountService(mockAccDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isBalanceValid_returnsTrue_givenValidValues() {

        //Arrange
        String val = "999";
        String val2 = "0.78";
        String val3 = "3344.32";

        //Act
        boolean expectedResult1 = sut.isBalanceValid(val);
        boolean expectedResult2 = sut.isBalanceValid(val2);
        boolean expectedResult3 = sut.isBalanceValid(val3);


        //Assert
        Assert.assertTrue("Expected value to be valid ", expectedResult1);
        Assert.assertTrue("Expected value to be valid ", expectedResult2);
        Assert.assertTrue("Expected value to be valid ", expectedResult3);
    }

    @Test
    public void test_isBalanceValid_returnsFalse_givenInvalidValues() {

        //Arrange
        String val = "-10";
        String val2 = "0";
        String val3 = " ";

        //Act
        boolean result1 = sut.isBalanceValid(val);
        boolean result2 = sut.isBalanceValid(val2);
        boolean result3 = sut.isBalanceValid(val3);


        //Assert
        Assert.assertFalse("Expected value to be invalid ", result1);
        Assert.assertFalse("Expected value to be invalid ", result2);
        Assert.assertFalse("Expected value to be invalid ", result3);
    }

//    @Test
//    public void test_withdrawFromAcc_returnsFalse_givenValueHigherThanBalance() throws Exception{
//
//        //Arrange
//        Account validAccount = new Account("valid","valid", "100");
//        String expectedMsg = "You cannot withdraw more than the account balance.";

        //Act
//        try {
//            boolean result = sut.withdrawFromAcc(validAccount,"200");
//        }finally {
            //Expect error here?
//        }

        //Assert
//        Assert.assertFalse("Expected value to be invalid ", result);

//    }

    @Test
    public void test_withdrawFromAcc_returnsTrue_givenValueLowerThanBalance() {

        //Arrange
        Account validAccount = new Account("valid","valid", "100");

        //Act
        Boolean result1 = sut.withdrawFromAcc(validAccount,"99");

        //Assert
        Assert.assertFalse("Expected new account to be valid ", result1);

    }

}
