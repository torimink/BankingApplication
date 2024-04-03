package com.example.Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.FileWriter;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class BankApplicationTests {

	private HashMap<String, BankAccount> accounts;
	private  BankAccount account1;
	private BankAccount account2;

	@BeforeEach
	void setUp() {
		accounts = new HashMap<>();
		account1 = new BankAccount(50, 1, "Account 1");
		account2 = new BankAccount(100, 2, "Account 2");
		accounts.put(account1.getAccountId(), account1);
		accounts.put(account2.getAccountId(), account2);
	}

	@Test
	void initializeAccounts() {
		assertEquals(2, accounts.size());
		assertTrue(accounts.containsKey(account1.getAccountId()));
		assertTrue(accounts.containsKey(account2.getAccountId()));
	}

	@Test
	void testDepositForAcc1() {
		BankAccount account = new BankAccount(100, 1, "Account 1");
		double depositAmount = 50;
		account.deposit(depositAmount);
		double expectedBalance = 100 + depositAmount;
		assertEquals(expectedBalance, account.getBalance(), 0.01, "Balance updated after deposit");
	}

	@Test
	void testDepositForAcc2() {
		BankAccount account = new BankAccount(100, 2, "Account 2");
		double depositAmount = 50;
		account.deposit(depositAmount);
		double expectedBalance = 100 + depositAmount;
		assertEquals(expectedBalance, account.getBalance(), 0.01, "Balance updated after deposit");
	}

	@Test
	void testWithdrawForAcc1() {
		BankAccount account = new BankAccount(100, 1, "Account 1");
		double withdrawAmount = 50;
		account.withdraw(withdrawAmount);
		double expectedBalance = 100 - withdrawAmount;
		assertEquals(expectedBalance, account.getBalance(), 0.01, "Balance updated after withdrawal");
	}

	@Test
	void testWithdrawForAcc2() { // make in a single?
		BankAccount account = new BankAccount(100, 2, "Account 2");
		double withdrawAmount = 50;
		account.withdraw(withdrawAmount);
		double expectedBalance = 100 - withdrawAmount;
		assertEquals(expectedBalance, account.getBalance(), 0.01, "Balance updated after withdrawal");
	}

	@Test
	void testTransferFrom1To2() {

	}

	@Test
	void generateAccountReport() {
		BankApplication
				.setAccounts(accounts);   // remake?
		BankApplication.generateAccountReport();
	}
}

