package com.samphora;

import java.util.ArrayList;
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
                    addDeposit();
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

    public static void addDeposit() {}
    public static void makePayment() {}
    public static void displayLedgerScreen() {}

}
