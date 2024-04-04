package com.example.Bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/accounts")
public class BankController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    @GetMapping("/")
    public String home() {
        return "Welcome to your Bank Application!";
    }  // Testinimui

        @GetMapping("/{accountId}/balance")
        public ResponseEntity<Double> getBalance(@PathVariable String accountId) {
            System.out.println("Getting balance for account ID: " + accountId);
            HashMap<String, BankAccount> accounts = BankApplication.getAccounts();
            if (accounts.containsKey(accountId)) {
                return ResponseEntity.ok(accounts.get(accountId).getBalance());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    @PostMapping("/{accountId}/deposit") // http://localhost:8080/api/accounts/1/deposit?amount=20
    public String deposit(@PathVariable String accountId, @RequestParam double amount) {
        logger.info("Attempting to deposit {} to account {}", amount, accountId);
        HashMap<String, BankAccount> accounts = BankApplication.getAccounts();
        if (accounts.containsKey(accountId)) {
            BankAccount account = accounts.get(accountId);
            account.deposit(amount);
            logger.info("Deposit successful. New balance: {}", account.getBalance());
            return "Deposit successful. New balance: " + account.getBalance();
        } else {
            logger.warn("Attempted to deposit to a non-existing account: {}", accountId);
            throw new RuntimeException("Account not found");
        }
    }

    @PostMapping("/{accountId}/withdraw") // http://localhost:8080/api/accounts/1/withdraw?amount=20
    public ResponseEntity<String> withdraw(@PathVariable String accountId, @RequestParam double amount) {
        logger.info("Request to withdraw {} from account {}", amount, accountId);
        HashMap<String, BankAccount> accounts = BankApplication.getAccounts();
        if (accounts.containsKey(accountId)) {
            BankAccount account = accounts.get(accountId);
            try {
                account.withdraw(amount);
                logger.info("Withdrawal successful. New balance for account {}: {}", accountId, account.getBalance());
                return ResponseEntity.ok("Withdrawal successful. New balance: " + account.getBalance());
            } catch (IllegalArgumentException e) {
                logger.warn("Withdrawal failed for account {}: {}", accountId, e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            logger.warn("Withdrawal attempted on non-existing account: {}", accountId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{sourceAccountId}/transfer") // http://localhost:8080/api/accounts/1/transfer?destinationAccountId=2&amount=30
    public String transfer(@PathVariable String sourceAccountId, @RequestParam String destinationAccountId, @RequestParam double amount) {
        logger.info("Attempting to transfer {} from account {} to account {}", amount, sourceAccountId, destinationAccountId);
        HashMap<String, BankAccount> accounts = BankApplication.getAccounts();
        if (accounts.containsKey(sourceAccountId) && accounts.containsKey(destinationAccountId)) {
            BankAccount sourceAccount = accounts.get(sourceAccountId);
            BankAccount destinationAccount = accounts.get(destinationAccountId);
            sourceAccount.transfer(destinationAccount, amount);
            logger.info("Transfer successful. New balance of your account: {}, New balance of destination account: {}", sourceAccount.getBalance(), destinationAccount.getBalance());
            return "Transfer successful. New balance of your account: " + sourceAccount.getBalance() + ", New balance of destination account: " + destinationAccount.getBalance();
        } else {
            logger.warn("One or both accounts not found. Source: {}, Destination: {}", sourceAccountId, destinationAccountId);
            throw new RuntimeException("One or both accounts not found");
        }
    }
}


