package com.moghies.SpendingTracker.model;

/**
 * Created by mmogh on 5/22/2016.
 */
public class ExpenseType implements Comparable<ExpenseType> {

    private String strValue;

    public ExpenseType(String strValue) {
        this.strValue = strValue;
    }

    public String getStrValue() {
        return strValue;
    }

    @Override
    public String toString() {
        return strValue;
    }

    @Override
    public int compareTo(ExpenseType another) {
        return strValue.compareTo(another.getStrValue());
    }
}
