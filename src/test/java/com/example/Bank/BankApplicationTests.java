package com.example.Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankApplicationTests {

    private BankAccount account;

    @BeforeEach
    public void setUp() {
        account = new BankAccount(100, 123456, "TestAccount");
    }

    @Test
    public void testGetAccountNumber() {
        assertEquals(123456, account.getAccountNumber());
    }

    @Test
    public void testGetAccountId() {
        assertEquals("TestAccount", account.getAccountId());
    }

    @Test
    public void testGetBalance() {
        assertEquals(100, account.getBalance());
    }

    @Test
    public void testDeposit() {
        account.deposit(50);
        assertEquals(150, account.getBalance());
    }

    @Test
    public void testWithdrawWithSufficientFunds() {
        account.withdraw(50);
        assertEquals(50, account.getBalance());
    }

    @Test
    public void testWithdrawWithInsufficientFunds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150);
        });
        String expectedMessage = "Insufficient funds for withdraw";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTransferWithSufficientFunds() {
        BankAccount destinationAccount = new BankAccount(0, 654321, "DestinationAccount");
        account.transfer(destinationAccount, 50);
        assertEquals(50, account.getBalance());
        assertEquals(50, destinationAccount.getBalance());
    }

    @Test
    public void testTransferWithInsufficientFunds() {
        BankAccount destinationAccount = new BankAccount(0, 654321, "DestinationAccount");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.transfer(destinationAccount, 150);
        });
        String expectedMessage = "Insufficient funds";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}


