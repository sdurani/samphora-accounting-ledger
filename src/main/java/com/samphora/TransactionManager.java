package com.samphora;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionManager {

    private ArrayList <Transaction> transactionList = new ArrayList<>();

    // addTransaction(): Adds a new transaction to the list
    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    // saveTransactionToFile(): Writes a new transaction to the CSV file
    public void saveTransactionToFile(Transaction transaction){

        // Determine the path to the transaction CSV file
        File file = new File("Transactions/transactions.csv");

        // Create the transactions folder if it doesn't exist already
        new File("Transactions").mkdirs();

        // Open the transactions CSV file for appending new transactions to it
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file,true))) {

            // Check if the file is empty
            if (file.length() == 0) {
                // Write the header for the file
                bufWriter.write("date|time|description|vendor|amount");
                bufWriter.newLine();
            }

            // Write the transaction in the file
            bufWriter.write(transaction.toString());
            bufWriter.newLine();

        } catch (IOException e) {
            System.out.println("Error in saving transaction to file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // loadTransactionsFromFile(): Loads previously saved transactions from the CSV into the list
    public void loadTransactionsFromFile() {

        // Determine the path to the pre-existing transactions CSV file
        File file = new File("Transactions/transactions.csv");

        // Clear the list before loading the pre-existing transactions
        transactionList.clear();

        // Stop the method if there is no pre-existing file
        if (!file.exists()) {
            return;
        }

        // Open the transactions file
        try (BufferedReader bufReader = new BufferedReader(new FileReader(file))) {

            // Skip the header line
            bufReader.readLine();

            // Initialize variable to store a transaction line
            String line;

            // Read each transaction's info
            while ((line = bufReader.readLine()) != null) {
                // Before splitting at the pipe: date|time|description|vendor|amount
                String[] lineSplitParts = line.split("\\|");
                // After splitting at the pipe: parts[0] = date, parts[1] = time, etc...

                // Convert and store the fields appropriately
                LocalDate date = LocalDate.parse(lineSplitParts[0]);
                LocalTime time = LocalTime.parse(lineSplitParts[1]);
                String description = lineSplitParts[2];
                String vendor = lineSplitParts[3];
                double amount = Double.parseDouble(lineSplitParts[4]);

                // Create a Transaction object using the fields
                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                // Add the transaction from the CSV to the list of transactions
                transactionList.add(transaction);
            }

        } catch (Exception e) {
            System.out.println("Error in opening transactions file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Since transactions list is private, this method allows access to a COPY of the list in other classes
    public ArrayList<Transaction> getAllTransactions() {
       // Returns a copy of transactions list
        return new ArrayList<>(transactionList);
    }

    // getDeposits(): Returns the transactions that are deposits
    public ArrayList<Transaction> getDeposits() {

        // Initialize a list of Transaction objects for deposits
        ArrayList<Transaction> deposits = new ArrayList<>();

        // Loop through each transaction in the list of all transactions
        for (Transaction transaction : transactionList) {
            // Check that the amount field is positive
            if (transaction.getAmount() > 0) {
                // Add the matching transaction to deposits list
                deposits.add(transaction);
            }
        }
        // Return the deposits list
        return deposits;
    }

    // getPayments(): Returns the transactions that are payments
    public ArrayList<Transaction> getPayments() {

        // Initialize a list of Transaction objects for payments
        ArrayList<Transaction> payments = new ArrayList<>();

        // Loop through each transaction in the list of all transactions
        for (Transaction transaction : transactionList) {
            // Check that the amount field is negative
            if (transaction.getAmount() < 0) {
                // Add the matching transaction to the payments list
                payments.add(transaction);
            }
        }
        // Return the payments list
        return payments;
    }
}
