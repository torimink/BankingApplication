package com.example.Bank;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BankViewController {

    private final BankAccountService bankAccountService;

    public BankViewController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index"; // for 'index.html'
    }

    @GetMapping("/accounts")
    public String listAccounts(Model model) {
        model.addAttribute("accounts", bankAccountService.getAccounts());
        return "accounts"; // for accounts.html
    }

    @GetMapping("/accounts/{accountId}")
    public String viewAccount(@PathVariable String accountId, Model model) {
        BankAccount account = bankAccountService.getAccountById(accountId);
        model.addAttribute("account", account);
        return "account-details"; // for any acc details account-details.html ?
    }

}
