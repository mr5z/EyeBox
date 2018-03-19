package com.nkraft.eyebox.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ViewUtils {

    private static ViewUtils _instance;
    public static ViewUtils instance(Context context) {
        if (_instance == null) {
            _instance = new ViewUtils(context);
        }
        return _instance;
    }

    private Context context;

    private ViewUtils(Context context) {
        this.context = context;
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}