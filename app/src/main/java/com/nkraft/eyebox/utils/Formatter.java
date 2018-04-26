package com.nkraft.eyebox.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault());

    public static String string(String message, Object ...args) {
        return String.format(Locale.getDefault(), message, args);
    }

    public static String date(long date) {
        return simpleDateFormat.format(new Date(date));
    }

    public static String currency(double amount) {
        return currency(amount, true);
    }

    public static String currency(double amount, boolean useGrouping) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(useGrouping);
        return numberFormat.format(amount);
    }
}
