package com.samphora;

import java.io.*;
import java.util.ArrayList;

public class TransactionManager {

    private ArrayList <Transaction> transactionList = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    public void saveTransactionToFile(Transaction transaction){

        // Create the transactions file in the Transactions folder
        File file = new File("Transactions/transactions.csv");

        // Create the transactions folder if it doesn't exist already
        new File("Transactions").mkdirs();

        // Create the transactions file as a CSV
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

}
