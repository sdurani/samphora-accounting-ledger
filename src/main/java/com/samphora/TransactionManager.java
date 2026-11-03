package com.samphora;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class TransactionManager {

    private ArrayList <Transaction> transactionList = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

}
