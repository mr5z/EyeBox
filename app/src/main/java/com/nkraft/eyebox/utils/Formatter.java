package com.nkraft.eyebox.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public static String string(String message, Object ...args) {
        return String.format(Locale.getDefault(), message, args);
    }

    public static String date(long date) {
        if (date <= 0)
            return null;
        return simpleDateFormat.format(new Date(date));
    }

    public static String time(long time) {
        if (time <= 0)
            return null;
        return simpleTimeFormat.format(new Date(time));
    }

    public static String currency(double amount) {
        return currency(amount, true);
    }

    public static String currency(double amount, boolean useGrouping) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setGroupingUsed(useGrouping);
        return formatter.format(amount);
    }
}
