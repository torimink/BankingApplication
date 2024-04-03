package com.example.Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.example.Bank.App.logger;

@SpringBootApplication
public class BankApplication {
	private static HashMap<String, BankAccount> accounts = new HashMap<>();
	static BankAccount account1;
	static BankAccount account2;


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
		logger.info("Application started"); // logger here?
		Scanner scanner = new Scanner(System.in);
		initializeAccounts();
		boolean exit = false;

		while (!exit) {
			System.out.println("Select an option: ");
			System.out.println("1. Account 1");
			System.out.println("2. Account 2");
			System.out.println("3. Exit");

			try {
				int choice = scanner.nextInt();
				logger.debug("User input: " + choice);

				switch (choice) {
					case 1:
						accountFunc(scanner, account1);
						break;
					case 2:
						accountFunc(scanner, account2);
						break;
					case 3:
						exit = true;
						break;
					default:
						logger.warn("Invalid choice: " + choice); //logger
						System.out.println("Invalid choice, please try again");
				}
			} catch (InputMismatchException e) {
				logger.error("Invalid inpt: " + e.getMessage()); //loger
				System.out.println("Invalid input. Please enter a valid integer choice.");
				scanner.next();
			}
		}

		generateAccountReport();
		logger.info("Generating finished");
	}

	public static void accountFunc(Scanner scanner, BankAccount account) {
		while (true) {
			System.out.println("\nSelect option " + account.getAccountNumber() + ":");
			System.out.println("1. Print Balance");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Transfer to another account");
			System.out.println("5. Back to main menu");
			int choice = scanner.nextInt();
			logger.debug("User input: " + choice);

			switch (choice) {
				case 1:
					account.printBalance();
					break;
				case 2:
					System.out.println("Enter amount to deposit:");
					double depositAmount = scanner.nextDouble();
					logger.debug("Deposit amount: " + depositAmount);
					account.deposit(depositAmount);
					account.printBalance();
					break;
				case 3:
					System.out.println("Enter amount to withdraw:");
					double withdrawAmount = scanner.nextDouble();
					logger.debug("Deposit amount: " + withdrawAmount);
					account.withdraw(withdrawAmount);
					account.printBalance();
					break;
				case 4:
					System.out.println("Enter amount to transfer:");
					double transferAmount = scanner.nextDouble();
					logger.debug("Transfer amount: " + transferAmount);
					System.out.println("Enter account number to transfer to (1 or 2):");
					int transferToAccount = scanner.nextInt();
					logger.debug("Destination account: " + transferToAccount);
					BankAccount destinationAccount = (transferToAccount == 1) ? account1 : account2;
					if (account.getBalance() >= transferAmount) { // if ??
						account.transfer(destinationAccount, transferAmount);
					} else {
						System.out.println("Insuficient funds for transfer");
					}
					break;
				case 5:
					logger.info("Exiting account " + account.getAccountNumber());
					return;
				default:
					logger.warn("Invalid choice: " + choice); // put logger
					System.out.println("Invalid choice, please try again");
			}

		}

	}
	public static void initializeAccounts() {
		logger.info("Initializing accounts");
		account1 = new BankAccount(50, 1, "Account 1");
		account2 = new BankAccount(100, 2, "Account 2");
		accounts.put(account1.getAccountId(), account1);
		accounts.put(account2.getAccountId(), account2);
		logger.info("Accounts initialized"); //logger
	}

	public static void setAccounts(HashMap<String, BankAccount> accounts) {
		BankApplication.accounts = accounts;
	}
	//    logger.info("Generating account report");
	public static void generateAccountReport() {
		try (FileWriter writer = new FileWriter("account_report.txt")) {
			for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
				String accountInfo = entry.getKey() + ": " + entry.getValue().getBalance();
				writer.write(accountInfo);
				writer.write(System.lineSeparator());
			}
//            System.out.println("Account report generated successfully.");
			logger.info("Account report generated successfully."); // log
		} catch (IOException e) {
			logger.error("Error generating account report: " + e.getMessage()); // log
			System.out.println("Error generating account report: " + e.getMessage());
		}
	}
}
