package com.moghies.SpendingTracker.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mmogh on 5/23/2016.
 */
public class ExpenseDate implements Serializable {

    private int month;
    private int day;
    private int year;

    //format must be month/day/year
    // return null if something goes wrong
    public static ExpenseDate Parse(String str) {
        String split[] = str.split("/");
        if (split.length != 3) {
            return null;
        }

        int month = Integer.parseInt(split[0]);
        int day = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);

        return new ExpenseDate(month, day, year);
    }

    public static ExpenseDate Now() {
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);

        return new ExpenseDate(month, day, year);
    }

    public ExpenseDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
