package com.nkraft.eyebox.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Debug {

    private static final String TAG = "eyebox";

    private static final DateFormat dateFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());

    public static void log(String message, Object ...args) {
        Log.d(TAG, String.format(timestamp() + " > " + message, args));
    }

    private static String timestamp() {
        return dateFormatter.format(new Date());
    }
}
