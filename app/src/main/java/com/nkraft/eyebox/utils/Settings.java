package com.nkraft.eyebox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    private static final String USER_ID = "USER_ID";

    private static  Settings _instance;
    private SharedPreferences preferences;

    private  Settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Settings instance(Context context) {
        if (_instance == null) {
            _instance = new Settings(context);
        }
        return _instance;
    }

    public void setUserId(long userId) {
        set(USER_ID, userId);
    }

    public long getUserId() {
        return preferences.getLong(USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return getUserId() != -1;
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    private void set(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    private void set(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    private void set(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    private void set(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

}