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

    // getPreviousMonth(): Returns filtered transactions that are from the previous month
    public ArrayList<Transaction> getPreviousMonth(ArrayList<Transaction> transactions){

        // Initialize a list to store transactions that are from the previous month
        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        // Initialize the current date
        LocalDate today = LocalDate.now();

        // Find the first day of the current month
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);

        // Find the first day of the previous month
        LocalDate firstDayOfPreviousMonth = firstDayOfCurrentMonth.minusMonths(1);

        // Loop through the transactions
        for (Transaction transaction : transactions) {
            if (!transaction.getDate().isBefore(firstDayOfPreviousMonth) && transaction.getDate().isBefore(firstDayOfCurrentMonth)) {
                // Add the valid transaction to the list of matching transactions
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // getYearToDate(): Returns filtered transactions from the first of year to the current date
    public ArrayList<Transaction> getYearToDate(ArrayList<Transaction> transactions){

        // Initialize a list to store the filtered transactions
        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        // Initialize the current date
        LocalDate today = LocalDate.now();

        // Initialize the current year
        int currentYear = today.getYear();

        // Loop through the transactions
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == currentYear && !transaction.getDate().isAfter(today)) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // getPreviousYear(): Returns filtered transactions from the previous year
    public ArrayList<Transaction> getPreviousYear(ArrayList<Transaction> transactions){

        // Initialize a list to store the filtered transactions
        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        // Initialize the current date
        LocalDate today = LocalDate.now();

        // Initialize the previous year
        int previousYear = today.getYear() - 1;

        // Loop through the transactions
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == previousYear) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // searchByVendor(): Finds transactions from a specific vendor
    public ArrayList<Transaction> searchByVendor(ArrayList<Transaction> transactions, String vendorToSearch){

        // Initialize a list to store the filtered transactions
        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        // Loop through the transactions
        for (Transaction transaction : transactions) {
            // Check for the search text in the vendors
            if (transaction.getVendor().toLowerCase().contains(vendorToSearch.toLowerCase())) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

}
