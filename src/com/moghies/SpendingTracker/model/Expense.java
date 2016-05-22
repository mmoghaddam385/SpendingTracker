package com.moghies.SpendingTracker.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by mmogh on 5/22/2016.
 */
public class Expense implements Serializable {

    private String description;
    private double price;
    private String type;

    public static Expense FromString(String str) {
        String[] split = str.split("\\|\\|\\|");

        if (split.length >= 3) {
            String desc = split[0];
            double price = Double.parseDouble(split[1]);
            String type = split[2];

            return new Expense(desc, price, type);
        }

        return null;
    }

    public Expense(String description, double price, String type) {
        this.price = price;
        this.description = description;
        this.type = type;
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

    @Override
    public String toString() {
        return description + "|||" + price + "|||" + type;
    }
}
