package com.samphora;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class Transaction {

    // Declare private variable fields for Transaction object
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;


    // CONSTRUCTOR
    // Creates a new Transaction instance, initializing all fields
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // GETTERS
    // Returns the date of the transaction
    public LocalDate getDate() {
        return date;
    }

    // Returns the time of the transaction
    public LocalTime getTime() {
        return time;
    }

    // Returns the description of the transaction
    public String getDescription() {
        return description;
    }

    // Returns the vendor of the transaction
    public String getVendor() {
        return vendor;
    }

    // Returns the amount of the transaction
    public double getAmount() {
        return amount;
    }

    // SETTERS
    // Sets the date of the transaction
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Sets the time of the transaction
    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Sets the description of the transaction
    public void setDescription(String description) {
        this.description = description;
    }

    // Sets the vendor of the transaction
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    // Sets the amount of the transaction
    public void setAmount(double amount) {
        this.amount = amount;
    }

//    @Override
//    public String toString() {
//        return String.format();
//    }
}
