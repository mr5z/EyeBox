package com.nkraft.eyebox.services;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.nkraft.eyebox.utils.Debug;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class PrinterService2 {

    PrinterService mService = PrinterService.instance();

    public static class PrinterCommands {
        public static final byte[] INIT = {27, 64};
        public static byte[] FEED_LINE = {10};

        public static byte[] SELECT_FONT_A = {27, 33, 0};

        public static byte[] SET_BAR_CODE_HEIGHT = {29, 104, 100};
        public static byte[] PRINT_BAR_CODE_1 = {29, 107, 2};
        public static byte[] SEND_NULL_BYTE = {0x00};

        public static byte[] SELECT_PRINT_SHEET = {0x1B, 0x63, 0x30, 0x02};
        public static byte[] FEED_PAPER_AND_CUT = {0x1D, 0x56, 66, 0x00};

        public static byte[] SELECT_CYRILLIC_CHARACTER_CODE_TABLE = {0x1B, 0x74, 0x11};

        public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33, (byte) 0xff, 3};
        public static byte[] SET_LINE_SPACING_24 = {0x1B, 0x33, 24};
        public static byte[] SET_LINE_SPACING_30 = {0x1B, 0x33, 30};

        public static byte[] TRANSMIT_DLE_PRINTER_STATUS = {0x10, 0x04, 0x01};
        public static byte[] TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = {0x10, 0x04, 0x02};
        public static byte[] TRANSMIT_DLE_ERROR_STATUS = {0x10, 0x04, 0x03};
        public static byte[] TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = {0x10, 0x04, 0x04};
    }

    BitSet dots;

    public String convertBitmap(Bitmap inputBitmap) {

        int mWidth = inputBitmap.getWidth();
        int mHeight = inputBitmap.getHeight();

        convertArgbToGrayscale(inputBitmap, mWidth, mHeight);
        String mStatus = "ok";
        return mStatus;

    }
    public void print_image(Bitmap bmp) throws IOException {
        convertBitmap(bmp);
        mService.write(PrinterCommands.SET_LINE_SPACING_24);

        int offset = 0;
        int w = bmp.getWidth();
        byte[] bb = ByteBuffer.allocate(4).putInt(w).array();
        while (offset < bmp.getHeight()) {
            PrinterCommands.SELECT_BIT_IMAGE_MODE[3] = bb[2];
            PrinterCommands.SELECT_BIT_IMAGE_MODE[4] = bb[3];
            mService.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
            for (int x = 0; x < bmp.getWidth(); ++x) {

                for (int k = 0; k < 3; ++k) {

                    byte slice = 0;
                    for (int b = 0; b < 8; ++b) {
                        int y = (((offset / 8) + k) * 8) + b;
                        int i = (y * bmp.getWidth()) + x;
                        boolean v = false;
                        if (i < dots.length()) {
                            v = dots.get(i);
                        }
                        slice |= (byte) ((v ? 1 : 0) << (7 - b));
                    }
                    mService.write(new byte[] { slice });
                }
            }
            offset += 24;
            mService.write(PrinterCommands.FEED_LINE);
            mService.write(PrinterCommands.FEED_LINE);
            mService.write(PrinterCommands.FEED_LINE);
            mService.write(PrinterCommands.FEED_LINE);
            mService.write(PrinterCommands.FEED_LINE);
            mService.write(PrinterCommands.FEED_LINE);
        }
        mService.write(PrinterCommands.SET_LINE_SPACING_30);
    }
    private void convertArgbToGrayscale(Bitmap bmpOriginal, int width,
                                        int height) {
        int pixel;
        int k = 0;
        int B = 0, G = 0, R = 0;
        dots = new BitSet();
        try {

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    // get one pixel color
                    pixel = bmpOriginal.getPixel(y, x);

                    // retrieve color of all channels
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    // take conversion up to one single value by calculating
                    // pixel intensity.
                    R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                    // set bit into bitset, by calculating the pixel's luma
                    if (R < 55) {
                        dots.set(k);//this is the bitset that i'm printing
                    }
                    k++;

                }


            }


        } catch (Exception e) {
            Debug.log("Error: %s", e.getMessage());
        }
    }
}
