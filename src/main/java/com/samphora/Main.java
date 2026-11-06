package com.samphora;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

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
        System.out.println("✓ Transactions loaded successfully.");

        // Display Samphora title
        displaySamphoraHeader();

        // Start the home menu
        displayHomeScreen(scanner, transactionManager, reportManager);

        // Close scanner after program exit selected
        scanner.close();
    }


    public static void displaySamphoraHeader() {

        System.out.println("\n");
        System.out.println("/".repeat(72));
        System.out.println("\\".repeat(72));
        System.out.println("========================================================================");
        System.out.println("                         S A M P H O R A");
        System.out.println("                        ACCOUNTING LEDGER");
        System.out.println("========================================================================");
        System.out.println("/".repeat(72));
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
                    System.out.println("! Invalid menu choice. Please enter 0, 1, 2, or 3.");
            }
        }
    }

    public static void addDeposit(Scanner scanner, TransactionManager transactionManager) {

        // Initialize user input variables
        String description = "";
        String vendor = "";
        double amount = 0;

        // Initialize input validation variable as false to start the validation loop
        boolean isValidDeposit = false;
        // Prompt the user for deposit description in the validation loop
        while (!isValidDeposit) {
            System.out.println("Enter deposit description: ");
            description = scanner.nextLine().trim();
            // Handle empty input edge case
            if (description.isEmpty()) {
                System.out.println("ERROR: Description cannot be empty. Please try again.");
            } else {
                // Description passes validation
                isValidDeposit = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValidDeposit = false;
        // Prompt the user for deposit vendor in the validation loop
        while (!isValidDeposit) {
            System.out.println("Enter deposit vendor: ");
            vendor = scanner.nextLine().trim();
            // Handle empty input edge case
            if (vendor.isEmpty()) {
                System.out.println("ERROR: Vendor cannot be empty. Please try again.");
            } else {
                isValidDeposit = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValidDeposit = false;
        // Prompt the user for deposit amount in the validation loop
        while (!isValidDeposit) {
            System.out.println("Enter deposit $ amount: ");
            // Try-catch handles inputs that can NOT be converted into numbers
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                // Handles inputs that are numbers BUT are negative or too big or $0 and therefore, NOT a valid deposit
                // Rejects $0, negative numbers, and infinity
                if (amount <= 0 || !Double.isFinite(amount)) {
                    System.out.println("Invalid amount. Please enter a positive real number.");
                } else {
                    isValidDeposit = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive real number.");
            }
        }

        // Call addTransaction to add the new deposit object to the list
        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("✦ Deposit successfully recorded.");
    }
    public static void makePayment(Scanner scanner, TransactionManager transactionManager) {
        // Initialize user input variables
        String description = "";
        String vendor = "";
        double amount = 0;

        // Initialize input validation variable as false to start the validation loop
        boolean isValidPayment = false;
        // Prompt the user for payment description in the validation loop
        while (!isValidPayment) {
            System.out.println("Enter payment description: ");
            description = scanner.nextLine().trim();
            // Handle empty input edge case
            if (description.isEmpty()) {
                System.out.println("ERROR: Description cannot be empty. Please try again.");
            } else {
                // Description passes validation
                isValidPayment = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValidPayment = false;
        // Prompt the user for payment vendor in the validation loop
        while (!isValidPayment) {
            System.out.println("Enter payment vendor: ");
            vendor = scanner.nextLine().trim();
            // Handle empty input edge case
            if (vendor.isEmpty()) {
                System.out.println("ERROR: Vendor cannot be empty. Please try again.");
            } else {
                isValidPayment = true;
            }
        }

        // Reset the validation variable to continue to the next prompt
        isValidPayment = false;
        // Prompt the user for payment amount in the validation loop
        while (!isValidPayment) {
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
                    isValidPayment = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive real number.");
            }
        }

        // Call addTransaction to add the new payment object to the list
        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("✦ Payment successfully recorded.");
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
                    displayTransactions(transactionManager.getAllTransactions(), "all");
                    break;
                case "2":
                    displayTransactions(transactionManager.getDeposits(), "deposits");
                    break;
                case "3":
                   displayTransactions(transactionManager.getPayments(), "payments");
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
                    System.out.println("! Invalid menu choice. Please enter 0, 1, 2, 3, or 4.");
            }
        }
    }

    // displayTransactions(): Displays whatever transactions it receives
    public static void displayTransactions(ArrayList<Transaction> transactions, String displayTransactionType) {

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

            // Display transaction table header
            System.out.println("\nSAMPHORA / TRANSACTION HISTORY");
            System.out.println("-".repeat(72));

            System.out.printf("%-12s %-8s %-20s %-16s %12s\n",
                    "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

            System.out.println("-".repeat(72));

            // Display each transaction in a formatted row
            for (Transaction transaction : transactions) {

                // Format the time to hours and minutes
                String time = transaction.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

                // Format the amount with respective positive or negative sign
                String formattedAmount;

                if (transaction.getAmount() < 0) {
                    formattedAmount = String.format("-$%,.2f", Math.abs(transaction.getAmount()));
                } else {
                    formattedAmount = String.format("+$%,.2f", transaction.getAmount());
                }

                System.out.printf("%-12s %-8s %-20.20s %-16.16s %12s\n",
                        transaction.getDate(),
                        time,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        formattedAmount);
            }

            // Display the summary for the transaction view
            displayTransactionSummary(transactions, displayTransactionType);
        }
    }

    public static void displayTransactionSummary(ArrayList<Transaction> transactions, String displayTransactionType) {

        // Initialize variables to store transaction totals
        double totalRevenue = 0;
        double totalExpenses = 0;
        // Initialize variables to store number of transactions
        int depositCount = 0;
        int paymentCount = 0;

        // Calculate totals for the transactions
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                // Update total revenue with each deposit transaction
                totalRevenue += transaction.getAmount();
                depositCount++;
            } else if (transaction.getAmount() < 0) {
                // Update total expenses with each payment transaction
                totalExpenses += Math.abs(transaction.getAmount());
                paymentCount++;
            }
        }

        double netIncome = totalRevenue - totalExpenses;

        // Format totals
        String formattedRevenue = String.format("+ $%,.2f", totalRevenue);
        String formattedExpenses = String.format("- $%,.2f", totalExpenses);
        String formattedNetIncome;

        if (netIncome < 0) {
            formattedNetIncome = String.format("- $%,.2f", Math.abs(netIncome));
        } else {
            formattedNetIncome = String.format("+ $%,.2f", netIncome);
        }

        // Display transaction summary
        System.out.println("-".repeat(72));

        if (displayTransactionType.equalsIgnoreCase("deposits")) {
            System.out.printf("%-59s %12s\n", "TOTAL DEPOSITS (" + depositCount + ")", formattedRevenue);

        } else if (displayTransactionType.equalsIgnoreCase("payments")) {
            System.out.printf("%-59s %12s\n", "TOTAL PAYMENTS (" + paymentCount + ")", formattedExpenses);

        } else {
            System.out.printf("%-59s %12s\n", "TOTAL REVENUE (" + depositCount + ")", formattedRevenue);

            System.out.printf("%-59s %12s\n", "TOTAL EXPENSES (" + paymentCount + ")", formattedExpenses);

            System.out.println("-".repeat(72));

            System.out.printf("%-59s %12s\n", "NET INCOME", formattedNetIncome);
        }

        System.out.println("=".repeat(72));
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
                    displayTransactions(reportManager.getMonthToDate(transactionManager.getAllTransactions()), "all");
                    break;
                case "2":
                    System.out.println("Previous Month");
                    displayTransactions(reportManager.getPreviousMonth(transactionManager.getAllTransactions()), "all");
                    break;
                case "3":
                    System.out.println("Year to Date");
                    displayTransactions(reportManager.getYearToDate(transactionManager.getAllTransactions()), "all");
                    break;
                case "4":
                    System.out.println("Previous Year");
                    displayTransactions(reportManager.getPreviousYear(transactionManager.getAllTransactions()), "all");
                    break;
                case "5":
                    System.out.println("Search by Vendor");
                    String vendorToSearch = "";

                    // Initialize input validation variable as false to start the validation loop
                    boolean isValidVendor = false;

                    // Prompt the user for a valid vendor name
                    while (!isValidVendor) {
                        System.out.println("Enter vendor name: ");
                        vendorToSearch = scanner.nextLine().trim();
                        // Handle empty input edge case
                        if (vendorToSearch.isEmpty()) {
                            System.out.println("ERROR: Vendor name cannot be empty. Please try again.");
                        } else {
                            isValidVendor = true;
                        }
                    }

                    displayTransactions(reportManager.searchByVendor(transactionManager.getAllTransactions(), vendorToSearch), "all");
                    break;
                case "6":
                    System.out.println("Custom Search");
                    System.out.println("Press enter to skip optional filters.");

                    // Initialize search field inputs
                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    String description;
                    String vendor;
                    String transactionType = "both";
                    // Skipped filters initialized
                    double minAmount = 0;
                    double maxAmount = 0;

                    String userInput;
                    boolean isValidInput = false;

                    // Get optional start date
                    while (!isValidInput) {
                        System.out.println("Enter start date (yyyy-MM-dd): ");
                        userInput = scanner.nextLine().trim();

                        if (userInput.isEmpty()) {
                            isValidInput = true;
                        } else {
                            try {
                                // Convert the string input into a date
                                startDate = LocalDate.parse(userInput);
                                isValidInput = true;
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date. Please try again in this format: yyyy-MM-dd.");
                            }
                        }
                    }

                    // Get optional end date
                    isValidInput = false;

                    while (!isValidInput) {
                        System.out.println("Enter end date (yyyy-MM-dd): ");
                        userInput = scanner.nextLine().trim();

                        if (userInput.isEmpty()) {
                            isValidInput = true;
                        } else {
                            try {
                                LocalDate userEndDate = LocalDate.parse(userInput);

                                if (startDate != null && userEndDate.isBefore(startDate)) {
                                    System.out.println("ERROR: End date cannot be before start date. Please try again.");
                                } else {
                                    endDate = userEndDate;
                                    isValidInput = true;
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date. Please try again in this format: yyyy-MM-dd.");
                            }
                        }
                    }

                    // Get optional description
                    System.out.println("Enter description (or press Enter to skip)");
                    description = scanner.nextLine().trim();

                    // Get optional vendor
                    System.out.println("Enter vendor (or press Enter to skip)");
                    vendor = scanner.nextLine().trim();

                    // Get transaction type as deposit or payment
                    isValidInput = false;

                    while (!isValidInput) {
                        System.out.println("[Required !] Deposit (D) or Payment (P) or Both (B):");
                        userInput = scanner.nextLine().trim();

                        if (userInput.equalsIgnoreCase("B")) {
                            transactionType = "both";
                            isValidInput = true;
                        } else if (userInput.equalsIgnoreCase("D")) {
                            transactionType = "deposit";
                            isValidInput = true;
                        } else if (userInput.equalsIgnoreCase("P")) {
                            transactionType = "payment";
                            isValidInput = true;
                        } else {
                            System.out.println("Invalid choice. Please enter D, P, or B.");
                        }
                    }

                    // Get optional minimum transaction amount
                    isValidInput = false;

                    while (!isValidInput) {
                        System.out.println("Enter minimum amount (or press Enter to skip): ");
                        userInput = scanner.nextLine().trim();

                        if (userInput.isEmpty()) {
                            isValidInput = true;
                        } else {
                            try {
                                double userMinAmount = Double.parseDouble(userInput);

                                if (userMinAmount <= 0 || !Double.isFinite(userMinAmount)) {
                                    System.out.println("Invalid amount. Please enter a positive real number.");
                                } else {
                                    minAmount = userMinAmount;
                                    isValidInput = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount. Please enter a positive real number.");
                            }
                        }
                    }

                    // Get optional maximum amount
                    isValidInput = false;

                    while (!isValidInput) {
                        System.out.println("Enter maximum amount (or press Enter to skip): ");
                        userInput = scanner.nextLine().trim();

                        if (userInput.isEmpty()) {
                            isValidInput = true;
                        } else {
                            try {
                                double userMaxAmount = Double.parseDouble(userInput);

                                if (userMaxAmount <= 0 || !Double.isFinite(userMaxAmount)) {
                                    System.out.println("Invalid amount. Please enter a positive real number.");
                                } else if (minAmount > 0 && userMaxAmount < minAmount) {
                                    System.out.println("ERROR: Maximum amount cannot be less than minimum amount.");
                                } else {
                                    maxAmount = userMaxAmount;
                                    isValidInput = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount. Please enter a positive real number.");
                            }
                        }
                    }

                    // Display matching filtered transactions
                    System.out.println("\nCustom Search Results\n");

                    displayTransactions(reportManager.customSearch(transactionManager.getAllTransactions(),
                            startDate, endDate, description, vendor, transactionType, minAmount, maxAmount), "all");

                    break;
                case "0":
                    System.out.println("Returning to Ledger...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("! Invalid menu choice. Please enter 0, 1, 2, 3, 4, 5, or 6.");
            }
        }
    }
}
