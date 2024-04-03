package com.example.Bank;

public class BankAccount {
    private double balance;
    private int accountNumber;
    private String accountId;

    public BankAccount(double initialBalance, int accountNumber, String accountId) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        this.accountId = accountId;
    }

    public int getAccountNumber() { // Getter mthd to allow other classes to retrieve acc number
        return this.accountNumber;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public double getBalance() {
        return this.balance;
    }

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void printBalance () {
        System.out.println("Current balance: " + balance);
    }

    public void transfer (BankAccount destinationAccount,double amount){
        if (amount <= balance) {
            withdraw(amount);
            destinationAccount.deposit(amount);
            System.out.println("Transfer successful");
        } else {
            System.out.println("Insufficient funds fot tranwfer");
        }
    }
}
