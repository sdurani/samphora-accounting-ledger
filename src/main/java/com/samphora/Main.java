package com.samphora;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Initialize Scanner object
        Scanner scanner = new Scanner(System.in);

        // Initialize TransactionManager object
        TransactionManager transactionManager = new TransactionManager();

        // Initialize ReportManager object
        ReportManager reportManager = new ReportManager();

        // Load saved transactions
        transactionManager.loadTransactionsFromFile();

        // Display Samphora title
        System.out.println("~*~*~*~*~*~*~* Samphora *~*~*~*~*~*~*~");
        System.out.println("[          ACCOUNTING LEDGER         ]");

        // Start the home menu
        displayHomeScreen(scanner, transactionManager, reportManager);

        // Close scanner after program exit selected
        scanner.close();


    }

    public static void displayHomeScreen(Scanner scanner, TransactionManager transactionManager, ReportManager reportManager) {
        boolean isRunning = true;

        while (isRunning) {

            System.out.println("\nHome\n");
            System.out.println("Please select an option:\n" +
                    "1) Make a deposit\n" +
                    "2) Make a payment\n" +
                    "3) View Ledger\n\n" +

                    "0) Exit\n" +
                    "> Enter choice (0, 1, 2, or 3): ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    addDeposit(scanner, transactionManager);
                    break;
                case "2":
                    makePayment(scanner, transactionManager);
                    break;
                case "3":
                    System.out.println("Accessing ledger...");
                    displayLedgerScreen(scanner, transactionManager, reportManager);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter 0, 1, 2, or 3.");
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

    public static void displayLedgerScreen(Scanner scanner, TransactionManager transactionManager, ReportManager reportManager) {

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nLedger\n");
            System.out.println("Please select an option:\n" +
                    "1) All Transactions\n" +
                    "2) Deposits\n" +
                    "3) Payments\n" +
                    "4) Reports\n\n" +

                    "0) Back to Home\n" +
                    "> Enter choice (0, 1, 2, 3, or 4): ");

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
                    System.out.println("Accessing reports...");
                    displayReportScreen(scanner, transactionManager, reportManager);
                    break;
                case "0":
                    System.out.println("Returning to Home...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter 0, 1, 2, 3, or 4.");
            }
        }
    }

    // displayTransactions(): Displays whatever transactions it receives
    public static void displayTransactions(ArrayList<Transaction> transactions) {

        // Check if the list of transactions is empty
        if (transactions.isEmpty()) {
            System.out.println("No matching transactions found.");
        }
        else {
            // Sort transactions list from newest to oldest by date, then by time
            transactions.sort((t1, t2) -> {
                // Compare dates from new in the list to older in the list
                int dateCompare = t2.getDate().compareTo(t1.getDate());

                // Check if the dates are the same, in which case, compare the times
                if (dateCompare == 0) {
                    int timeCompare = t2.getTime().compareTo(t1.getTime());
                    return timeCompare;
                } else {
                    return dateCompare;
                }
            });

            // Display each transaction to user
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public static void displayReportScreen(Scanner scanner, TransactionManager transactionManager, ReportManager reportManager) {

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nReports\n");
            System.out.println("Run report from...\n" +
                    "1) Month to Date\n" +
                    "2) Previous Month\n" +
                    "3) Year To Date\n" +
                    "4) Previous Year\n" +
                    "5) Search by Vendor\n" +
                    "6) Custom Search\n\n" +

                    "0) Back to Ledger\n" +
                    "> Enter choice (0, 1, 2, 3, 4, 5, or 6): ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    System.out.println("Month to Date");
                    displayTransactions(reportManager.getMonthToDate(transactionManager.getAllTransactions()));
                    break;
                case "2":
                    System.out.println("Previous Month");
                    displayTransactions(reportManager.getPreviousMonth(transactionManager.getAllTransactions()));
                    break;
                case "3":
                    System.out.println("Year to Date");
                    displayTransactions(reportManager.getYearToDate(transactionManager.getAllTransactions()));
                    break;
                case "4":
                    System.out.println("Previous Year");
                    displayTransactions(reportManager.getPreviousYear(transactionManager.getAllTransactions()));
                    break;
                case "5":
                    System.out.println("Search by Vendor");
                    String vendorToSearch = "";

                    // Initialize input validation variable as false to start the validation loop
                    boolean isValid = false;

                    // Prompt the user for a valid vendor name
                    while (!isValid) {
                        System.out.println("Enter vendor name: ");
                        vendorToSearch = scanner.nextLine().trim();
                        // Handle empty input edge case
                        if (vendorToSearch.isEmpty()) {
                            System.out.println("Vendor name cannot be empty. Please try again.");
                        } else {
                            isValid = true;
                        }
                    }

                    displayTransactions(reportManager.searchByVendor(transactionManager.getAllTransactions(), vendorToSearch));
                    break;
                case "6":
                    System.out.println("Custom Search");
                    break;
                case "0":
                    System.out.println("Returning to Ledger...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please enter 0, 1, 2, 3, 4, 5, or 6.");
            }
        }
    }
}
