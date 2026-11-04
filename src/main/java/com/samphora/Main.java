package com.samphora;

import java.time.LocalDate;
import java.time.LocalTime;
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
                    "3) View Ledger\n" +
                    "4) Exit\n" +
                    "> Enter choice (1, 2, 3, or 4): ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    addDeposit(scanner, transactionManager);
                    break;
                case "2":
                    makePayment();
                    break;
                case "3":
                    displayLedgerScreen();
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
                System.out.println("Invalid amount. Please enter a positive number.");
            }
        }

        // Call addTransaction to add the new transaction object to the list
        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("Deposit has been saved successfully!");
    }
    public static void makePayment() {}
    public static void displayLedgerScreen() {}

}
