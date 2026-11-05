package com.samphora;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReportManager {

    // getMonthToDate(): Returns filtered transactions that are within a month from the current date
    public ArrayList<Transaction> getMonthToDate(ArrayList<Transaction> transactions){

        // Initialize a list to store transactions that are within the current month from the date
        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        // Initialize the current date and the first day of the current month
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        // Loop through transactions
        for (Transaction transaction : transactions) {
            // Check if each date is between today AND the first day of the current month
            if (!transaction.getDate().isBefore(firstDayOfMonth) && !transaction.getDate().isAfter(today)) {
                // Add the valid transaction to the list of matching transactions
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

}
