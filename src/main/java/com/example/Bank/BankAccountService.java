package com.example.Bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BankAccountService {

    public BankAccount getAccountById(String accountId) {
        return bankAccountRepository.findById(Long.valueOf(accountId))
                .orElseThrow(() -> new RuntimeException("Account with id " + accountId + " not found"));
    }

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAccounts() {
        return bankAccountRepository.findAll();

    }

    public void addNewBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);

    }

    public void deleteAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }

    public void deposit(String accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);
    }

    public void withdraw(String accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new RuntimeException("Account not found"));
        double newBalance = account.getBalance() - amount;
        if(newBalance < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(newBalance);
        bankAccountRepository.save(account);
    }

    public void transfer(String sourceAccountId, String destinationAccountId, double amount) {
        BankAccount sourceAccount = bankAccountRepository.findById(Long.parseLong(sourceAccountId))
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        BankAccount destinationAccount = bankAccountRepository.findById(Long.parseLong(destinationAccountId))
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if(sourceAccount.getBalance() >= amount) {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
            bankAccountRepository.save(sourceAccount);
            bankAccountRepository.save(destinationAccount);
        } else {
            throw new RuntimeException("Insufficient funds in source account");
        }
    }
}

