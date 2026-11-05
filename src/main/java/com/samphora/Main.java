package com.samphora;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Initialize Scanner object
        Scanner scanner = new Scanner(System.in);

        // Initialize TransactionManager object
        TransactionManager transactionManager = new TransactionManager();

        // Load saved transactions
        transactionManager.loadTransactionsFromFile();

        // Display Samphora title
        System.out.println("~*~*~*~*~*~*~* Samphora *~*~*~*~*~*~*~");
        System.out.println("[          ACCOUNTING LEDGER         ]");

        // Start the home menu
        displayHomeScreen(scanner, transactionManager);

        // Close scanner after program exit selected
        scanner.close();


    }

    public static void displayHomeScreen(Scanner scanner, TransactionManager transactionManager) {
        boolean isRunning = true;

        while (isRunning) {

            System.out.println("\nHome\n");
            System.out.println("Please select an option:\n" +
                    "1) Make a deposit\n" +
                    "2) Make a payment\n" +
                    "3) View Ledger\n\n" +
                    "4) Exit\n" +
                    "> Enter choice (1, 2, 3, or 4): ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    addDeposit(scanner, transactionManager);
                    break;
                case "2":
                    makePayment(scanner, transactionManager);
                    break;
                case "3":
                    displayLedgerScreen(scanner, transactionManager);
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    public static void addDeposit(Scanner scanner, TransactionManager transactionManager) {

        // Initialize user input variables
        String description = "";
        String vendor = "";
        double amount = 0;

        // Initialize input validation variable as false to start the validation loop
        boolean isValid = false;
        // Prompt the user for deposit description in the validation loop
        while (!isValid) {
            System.out.println("Enter deposit description: ");
            description = scanner.nextLine().trim();
            // Handle empty input edge case
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty. Please try again.");
            } else {
                // Description passes validation
                isValid = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValid = false;
        // Prompt the user for deposit vendor in the validation loop
        while (!isValid) {
            System.out.println("Enter deposit vendor: ");
            vendor = scanner.nextLine().trim();
            // Handle empty input edge case
            if (vendor.isEmpty()) {
                System.out.println("Vendor cannot be empty. Please try again.");
            } else {
                isValid = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValid = false;
        // Prompt the user for deposit amount in the validation loop
        while (!isValid) {
            System.out.println("Enter deposit $ amount: ");
            // Try-catch handles inputs that can NOT be converted into numbers
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                // Handles inputs that are numbers BUT are negative or too big or $0 and therefore, NOT a valid deposit
                // Rejects $0, negative numbers, and infinity
                if (amount <= 0 || !Double.isFinite(amount)) {
                    System.out.println("Invalid amount. Please enter a positive real number.");
                } else {
                    isValid = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive real number.");
            }
        }

        // Call addTransaction to add the new deposit object to the list
        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("Deposit successful!");
    }
    public static void makePayment(Scanner scanner, TransactionManager transactionManager) {
        // Initialize user input variables
        String description = "";
        String vendor = "";
        double amount = 0;

        // Initialize input validation variable as false to start the validation loop
        boolean isValid = false;
        // Prompt the user for payment description in the validation loop
        while (!isValid) {
            System.out.println("Enter payment description: ");
            description = scanner.nextLine().trim();
            // Handle empty input edge case
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty. Please try again.");
            } else {
                // Description passes validation
                isValid = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValid = false;
        // Prompt the user for payment vendor in the validation loop
        while (!isValid) {
            System.out.println("Enter payment vendor: ");
            vendor = scanner.nextLine().trim();
            // Handle empty input edge case
            if (vendor.isEmpty()) {
                System.out.println("Vendor cannot be empty. Please try again.");
            } else {
                isValid = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValid = false;
        // Prompt the user for payment amount in the validation loop
        while (!isValid) {
            System.out.println("Enter payment $ amount (as a positive number): ");
            // Try-catch handles inputs that can NOT be converted into numbers
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                // Handles inputs that are numbers BUT are negative or too big or $0 and therefore, NOT a valid deposit
                // Rejects $0, negative numbers, and infinity
                if (amount <= 0 || !Double.isFinite(amount)) {
                    System.out.println("Invalid amount. Please enter a positive real number.");
                } else {
                    amount *= -1;
                    isValid = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive real number.");
            }
        }

        // Call addTransaction to add the new payment object to the list
        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("Payment successful!");
    }

    public static void displayLedgerScreen(Scanner scanner, TransactionManager transactionManager) {

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nLedger\n");
            System.out.println("Please select an option:\n" +
                    "1) All Transactions\n" +
                    "2) Deposits\n" +
                    "3) Payments\n" +
                    "4) Reports\n\n" +
                    "5) Back to Home\n" +
                    "> Enter choice (1, 2, 3, 4, or 5): ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    displayTransactions(transactionManager.getAllTransactions());
                    break;
                case "2":
                    displayTransactions(transactionManager.getDeposits());
                    break;
                case "3":
                   displayTransactions(transactionManager.getPayments());
                    break;
                case "4":
                    displayReportScreen();
                    break;
                case "5":
                    System.out.println("Returning to home...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter 1, 2, 3, 4, or 5.");
            }
        }
    }

    // displayTransactions(): Displays whatever transactions it receives
    public static void displayTransactions(ArrayList<Transaction> transactions) {
       // Check if the list of transactions is empty
        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }

        // Loops through the list passed through and prints each transaction in it
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void displayReportScreen() {}

}
