package com.nkraft.eyebox.utils;

import android.graphics.Bitmap;

public class BitmapUtils {

    public static Bitmap resizeBitmapByHeight(Bitmap bitmap, int height) {
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int width = Math.round(height * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public static Bitmap resizeBitmapByWidth(Bitmap bitmap, int width) {
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int height = Math.round(width / aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }
}
