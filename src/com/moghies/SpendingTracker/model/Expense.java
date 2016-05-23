package com.moghies.SpendingTracker.model;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by mmogh on 5/22/2016.
 */
public class Expense implements Serializable {

    private String description;
    private double price;
    private String type;
    private ExpenseDate date;

    public static Expense FromString(String str) {
        String[] split = str.split("\\|\\|\\|");

        if (split.length >= 3) {
            String desc = split[0];
            double price = Double.parseDouble(split[1]);
            String type = split[2];
            ExpenseDate date = null;
            if (split.length >= 4) {
                date = ExpenseDate.Parse(split[3]);
            }
            if (date == null){
                date = new ExpenseDate(5,5,2016);
            }

            return new Expense(desc, price, type, date);
        }

        return null;
    }

    public Expense(String description, double price, String type, ExpenseDate date) {
        this.price = price;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public String getStrPrice() {
        return "€" + price;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public ExpenseDate getDate() {
        return date;
    }

    public String getDateString() {
        return date.toString();
    }

    @Override
    public String toString() {
        return description + "|||" + price + "|||" + type + "|||" + date.toString();
    }
}
