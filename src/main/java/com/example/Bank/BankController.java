package com.example.Bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private final BankAccountService bankAccountService;

    @Autowired
    public BankController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public List<BankAccount> getAccounts() {
        return bankAccountService.getAccounts();
    }

    @PostMapping
    public ResponseEntity<String> registerNewBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountService.addNewBankAccount(bankAccount);
        logger.info("New bank account registered: {}", bankAccount);
        return ResponseEntity.ok("Account registered successfully");
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to your Bank Application!";
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String accountId) {
        try {
            BankAccount account = bankAccountService.getAccountById(accountId);
            return ResponseEntity.ok(account.getBalance());
        } catch (RuntimeException e) {
            logger.error("Account not found: {}", accountId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountId, @RequestParam double amount) {
        try {
            bankAccountService.deposit(accountId, amount);
            return ResponseEntity.ok("Deposit successful");
        } catch (RuntimeException e) {
            logger.error("Error during deposit: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountId, @RequestParam double amount) {
        try {
            bankAccountService.withdraw(accountId, amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (RuntimeException e) {
            logger.error("Error during withdrawal: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{sourceAccountId}/transfer")
    public ResponseEntity<String> transfer(@PathVariable String sourceAccountId, @RequestParam String destinationAccountId, @RequestParam double amount) {
        try {
            bankAccountService.transfer(sourceAccountId, destinationAccountId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (RuntimeException e) {
            logger.error("Error during transfer: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        logger.info("Attempting to delete account with ID: {}", id);
        try {
            bankAccountService.deleteAccount(id);
            logger.info("Account with ID: {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Attempted to delete a non-existing account with ID: {}", id, e);
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while deleting account with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


