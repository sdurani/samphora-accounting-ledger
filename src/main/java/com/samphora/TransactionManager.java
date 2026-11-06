package com.samphora;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionManager {

    private ArrayList <Transaction> transactionList = new ArrayList<>();

    // addTransaction(): Adds a new transaction to the list
    public void addTransaction(String description, String vendor, double amount){
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);

        // Save to the CSV file before updating the in-memory list
        saveTransactionToFile(transaction);

        transactionList.add(transaction);
    }

    // saveTransactionToFile(): Writes a new transaction to the CSV file
    public void saveTransactionToFile(Transaction transaction){

        // Determine the path to the transaction CSV file
        File file = new File("Transactions/transactions.csv");

        // Create the transactions folder if it doesn't exist already
        new File("Transactions").mkdirs();

        // Append transactions without overwriting existing records
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file,true))) {

            // Only write the CSV header when the file is new or empty
            if (file.length() == 0) {
                bufWriter.write("date|time|description|vendor|amount");
                bufWriter.newLine();
            }

            bufWriter.write(transaction.toString());
            bufWriter.newLine();

        } catch (IOException e) {
            System.out.println("Error in saving transaction to file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // loadTransactionsFromFile(): Loads previously saved transactions from the CSV into the list
    public void loadTransactionsFromFile() {

        File file = new File("Transactions/transactions.csv");

        // Clear the list before loading the pre-existing transactions to avoid duplicates when reloading the file
        transactionList.clear();

        // Stop the method if there is no pre-existing file
        if (!file.exists()) {
            return;
        }

        try (BufferedReader bufReader = new BufferedReader(new FileReader(file))) {

            bufReader.readLine();
            String line;

            while ((line = bufReader.readLine()) != null) {
                // Split each row using the pipe delimiter rather than a comma
                String[] lineSplitParts = line.split("\\|");

                LocalDate date = LocalDate.parse(lineSplitParts[0]);
                LocalTime time = LocalTime.parse(lineSplitParts[1]);
                String description = lineSplitParts[2];
                String vendor = lineSplitParts[3];
                double amount = Double.parseDouble(lineSplitParts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactionList.add(transaction);
            }

        } catch (Exception e) {
            System.out.println("Error in opening transactions file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Since transactions list is private, this method allows access to a shallow copy of the list in other classes
    public ArrayList<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionList);
    }

    // getDeposits(): Returns the transactions that are deposits
    public ArrayList<Transaction> getDeposits() {

        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            // Positive amounts represent deposits
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    // getPayments(): Returns the transactions that are payments
    public ArrayList<Transaction> getPayments() {

        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            // Negative amounts represent payments
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }
        return payments;
    }
}
