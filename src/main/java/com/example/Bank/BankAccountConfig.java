package com.example.Bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BankAccountConfig {

    @Bean
    CommandLineRunner anotherInit(BankAccountRepository repository) {
        return args -> {
            long count = repository.count();
            System.out.println("Current account count: " + count);
            if (repository.count() == 0) {
                BankAccount account1 = new BankAccount(50.0, 1, "1");
                BankAccount account2 = new BankAccount(100.0, 2, "2");
                BankAccount account3 = new BankAccount(1000.00, 3, "3");
                BankAccount account4 = new BankAccount(1500.00, 4, "4");

                repository.saveAll(List.of(account1, account2, account3, account4));
            }
        };
    }
}


