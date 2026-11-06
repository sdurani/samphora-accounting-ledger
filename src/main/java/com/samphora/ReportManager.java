package com.samphora;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReportManager {

    // getMonthToDate(): Returns filtered transactions that are within a month from the current date
    public ArrayList<Transaction> getMonthToDate(ArrayList<Transaction> transactions){

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        for (Transaction transaction : transactions) {
            // Check if each date is between today AND the first day of the current month
            if (!transaction.getDate().isBefore(firstDayOfMonth) && !transaction.getDate().isAfter(today)) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // getPreviousMonth(): Returns filtered transactions that are from the previous month
    public ArrayList<Transaction> getPreviousMonth(ArrayList<Transaction> transactions){

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfPreviousMonth = firstDayOfCurrentMonth.minusMonths(1);

        for (Transaction transaction : transactions) {
            // Include the previous month's first day, but exclude the current month's first day
            if (!transaction.getDate().isBefore(firstDayOfPreviousMonth) && transaction.getDate().isBefore(firstDayOfCurrentMonth)) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // getYearToDate(): Returns filtered transactions from the first of year to the current date
    public ArrayList<Transaction> getYearToDate(ArrayList<Transaction> transactions){

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        for (Transaction transaction : transactions) {
            // Include only transactions from the current year up to today
            if (transaction.getDate().getYear() == currentYear && !transaction.getDate().isAfter(today)) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // getPreviousYear(): Returns filtered transactions from the previous year
    public ArrayList<Transaction> getPreviousYear(ArrayList<Transaction> transactions){

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() - 1;

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == previousYear) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    // searchByVendor(): Finds transactions from a specific vendor
    public ArrayList<Transaction> searchByVendor(ArrayList<Transaction> transactions, String vendorToSearch){

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            // Allow partial vendor matches
            if (transaction.getVendor().toLowerCase().contains(vendorToSearch.toLowerCase())) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }

    public ArrayList<Transaction> customSearch(ArrayList<Transaction> transactions, LocalDate startDate, LocalDate endDate,
                                               String description, String vendor, String transactionType, double minAmount, double maxAmount) {

        ArrayList<Transaction> matchingTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {

            boolean doesMatch = true;

            // Check start date with null dates indicating skipped filters
            if (startDate != null && transaction.getDate().isBefore(startDate)) {
                doesMatch = false;
            }

            // Check end date with null dates indicating skipped filters
            if (endDate != null && transaction.getDate().isAfter(endDate)) {
                doesMatch = false;
            }

            // Check vendor
            if (!vendor.isEmpty() && !transaction.getVendor().toLowerCase().contains(vendor.toLowerCase())) {
                doesMatch = false;
            }

            // Check description
            if (!description.isEmpty() && !transaction.getDescription().toLowerCase().contains(description.toLowerCase())) {
                doesMatch = false;
            }

            // Check minimum transaction amount with 0 indicating a skipped amount filter
            if (minAmount > 0 && Math.abs(transaction.getAmount()) < minAmount) {
                doesMatch = false;
            }

            // Check maximum transaction amount with 0 indicating a skipped amount filter
            if (maxAmount > 0 && Math.abs(transaction.getAmount()) > maxAmount) {
                doesMatch = false;
            }

            // Check for deposit transactions
            if (transactionType.equalsIgnoreCase("deposit") && transaction.getAmount() < 0) {
                doesMatch = false;
            }

            // Check for payment transactions
            if (transactionType.equalsIgnoreCase("payment") && transaction.getAmount() > 0) {
                doesMatch = false;
            }

            // NOTE: When "both" is selected, neither transaction type is excluded

            // Assume each transaction matches until one of the selected filters excludes it
            if (doesMatch) {
                matchingTransactions.add(transaction);
            }
        }

        return matchingTransactions;
    }
}
