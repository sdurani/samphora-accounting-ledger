package com.samphora;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {

        // Initialize objects
        Scanner scanner = new Scanner(System.in);
        TransactionManager transactionManager = new TransactionManager();
        ReportManager reportManager = new ReportManager();

        // Display Samphora title
        displaySamphoraHeader();
        displayLoadingBar("Accessing Samphora Accounting Portal...");

        // Load saved transactions
        transactionManager.loadTransactionsFromFile();
        System.out.println("✓ Transactions loaded successfully.");

        displayHomeScreen(scanner, transactionManager, reportManager);

        scanner.close();
    }

    public static void displayLoadingBar(String message) {

        System.out.println("\n✦ " + message);

        for (int i = 0; i < 10; i++) {
            System.out.print("///");
            // Flush the output so the loading animation updates
            System.out.flush();

            try {
                Thread.sleep(190);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\nERROR: Loading animation interrupted.");
                return;
            }
        }
        System.out.println();
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

    public static void displayScreenHeader(String title) {
        String heading = "✦  " + title + "  ✦";
        System.out.println();
        System.out.println("-".repeat(44));
        System.out.println(" ".repeat((44 - heading.length()) / 2) + heading);
        System.out.println("-".repeat(44));
    }

    public static void displayHomeScreen(Scanner scanner, TransactionManager transactionManager, ReportManager reportManager) {
        boolean isRunning = true;

        while (isRunning) {

            displayScreenHeader("H O M E");
            System.out.println(
                    "\n" +
                            "   1)  Make a deposit\n" +
                            "   2)  Make a payment\n" +
                            "   3)  View Ledger\n" +
                            "\n" +
                            "   " + "-".repeat(38) + "\n" +
                            "   0)  Exit\n"
            );
            System.out.print("\n   > Enter choice: ");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    addDeposit(scanner, transactionManager);
                    break;
                case "2":
                    makePayment(scanner, transactionManager);
                    break;
                case "3":
                    displayLoadingBar("Accessing ledger...");
                    displayLedgerScreen(scanner, transactionManager, reportManager);
                    break;
                case "0":
                    System.out.println("\n" + "=".repeat(44));
                    System.out.println("        ✦ Thank you for using Samphora Accounting Portal!");
                    System.out.println("=".repeat(44));
                    isRunning = false;
                    break;
                default:
                    System.out.println("! Invalid menu choice. Please enter 0, 1, 2, or 3.");
            }
        }
    }

    public static void addDeposit(Scanner scanner, TransactionManager transactionManager) {

        String description = "";
        String vendor = "";
        double amount = 0;

        boolean isValidDeposit = false;

        while (!isValidDeposit) {
            System.out.println("Enter deposit description: ");
            description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println("ERROR: Description cannot be empty. Please try again.");
            } else {
                isValidDeposit = true;
            }
        }

        isValidDeposit = false;

        while (!isValidDeposit) {
            System.out.println("Enter deposit vendor: ");
            vendor = scanner.nextLine().trim();

            if (vendor.isEmpty()) {
                System.out.println("ERROR: Vendor cannot be empty. Please try again.");
            } else {
                isValidDeposit = true;
            }
        }

        isValidDeposit = false;

        while (!isValidDeposit) {
            System.out.println("Enter deposit $ amount: ");
            // Try-catch handles inputs that can NOT be converted into numbers
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
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

        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("✦ Deposit successfully recorded.");
    }
    public static void makePayment(Scanner scanner, TransactionManager transactionManager) {
        String description = "";
        String vendor = "";
        double amount = 0;

        boolean isValidPayment = false;

        while (!isValidPayment) {
            System.out.println("Enter payment description: ");
            description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println("ERROR: Description cannot be empty. Please try again.");
            } else {
                isValidPayment = true;
            }
        }

        isValidPayment = false;

        while (!isValidPayment) {
            System.out.println("Enter payment vendor: ");
            vendor = scanner.nextLine().trim();

            if (vendor.isEmpty()) {
                System.out.println("ERROR: Vendor cannot be empty. Please try again.");
            } else {
                isValidPayment = true;
            }
        }

        isValidPayment = false;

        while (!isValidPayment) {
            System.out.println("Enter payment $ amount (as a positive number): ");
            // Try-catch handles inputs that can NOT be converted into numbers
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                // Rejects $0, negative numbers, and infinity
                if (amount <= 0 || !Double.isFinite(amount)) {
                    System.out.println("Invalid amount. Please enter a positive real number.");
                } else {
                    // Store payments as negative amounts
                    amount *= -1;
                    isValidPayment = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive real number.");
            }
        }

        transactionManager.addTransaction(description, vendor, amount);
        System.out.println("✦ Payment successfully recorded.");
    }

    public static void displayLedgerScreen(Scanner scanner, TransactionManager transactionManager, ReportManager reportManager) {

        boolean isRunning = true;

        while (isRunning) {
            displayScreenHeader("L E D G E R");
            System.out.println(
                    "\n" +
                            "   1)  All Transactions\n" +
                            "   2)  Deposits\n" +
                            "   3)  Payments\n" +
                            "   4)  Reports\n" +
                            "\n" +
                            "   " + "-".repeat(38) + "\n" +
                            "   0)  Back to Home\n"
            );
            System.out.print("\n   > Enter choice: ");

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
                    displayLoadingBar("Accessing reports...");
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

        if (transactions.isEmpty()) {
            System.out.println("No matching transactions found.");
        }
        else {
            // Sort transactions list from newest to oldest by date, then by time
            transactions.sort((t1, t2) -> {
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

                String time = transaction.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                String formattedAmount;

                if (transaction.getAmount() < 0) {
                    formattedAmount = String.format("-$%,.2f", Math.abs(transaction.getAmount()));
                } else {
                    formattedAmount = String.format("+$%,.2f", transaction.getAmount());
                }

                // Align table columns
                System.out.printf("%-12s %-8s %-20.20s %-16.16s %12s\n",
                        transaction.getDate(),
                        time,
                        transaction.getDescription(),
                        transaction.getVendor(),
                        formattedAmount);
            }

            displayTransactionSummary(transactions, displayTransactionType);
        }
    }

    public static void displayTransactionSummary(ArrayList<Transaction> transactions, String displayTransactionType) {

        double totalRevenue = 0;
        double totalExpenses = 0;
        int depositCount = 0;
        int paymentCount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                totalRevenue += transaction.getAmount();
                depositCount++;
            } else if (transaction.getAmount() < 0) {
                // Use absolute values so expenses can be subtracted from revenue
                totalExpenses += Math.abs(transaction.getAmount());
                paymentCount++;
            }
        }

        double netIncome = totalRevenue - totalExpenses;

        String formattedRevenue = String.format("+ $%,.2f", totalRevenue);
        String formattedExpenses = String.format("- $%,.2f", totalExpenses);
        String formattedNetIncome;

        if (netIncome < 0) {
            formattedNetIncome = String.format("- $%,.2f", Math.abs(netIncome));
        } else {
            formattedNetIncome = String.format("+ $%,.2f", netIncome);
        }

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
            displayScreenHeader("R E P O R T S");
            System.out.println(
                    "\n" +
                            "   1)  Month to Date\n" +
                            "   2)  Previous Month\n" +
                            "   3)  Year to Date\n" +
                            "   4)  Previous Year\n" +
                            "   5)  Search by Vendor\n" +
                            "   6)  Custom Search\n" +
                            "\n" +
                            "   " + "-".repeat(38) + "\n" +
                            "   0)  Back to Ledger\n"
            );
            System.out.print("\n   > Enter choice: ");

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

                    boolean isValidVendor = false;

                    while (!isValidVendor) {
                        System.out.println("Enter vendor name: ");
                        vendorToSearch = scanner.nextLine().trim();

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

                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    String description;
                    String vendor;
                    String transactionType = "both";
                    // Zero indicates that an optional amount filter was skipped
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

                    displayLoadingBar("Searching transactions...");

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
