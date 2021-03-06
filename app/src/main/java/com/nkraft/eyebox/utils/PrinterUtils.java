package com.nkraft.eyebox.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class PrinterUtils {

    // UNICODE 0x23 = #
    public static final byte[] UNICODE_TEXT = new byte[] {0x23, 0x23, 0x23,
            0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,
            0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,
            0x23, 0x23, 0x23};

    public static byte[] decodeBitmap(Bitmap bmp){
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();

        List<String> list = new ArrayList<>(); //binaryString list
        StringBuffer sb;

        int zeroCount = bmpWidth % 8;

        String zeroStr = "";
        if (zeroCount > 0) {
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < bmpHeight; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < bmpWidth; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160)
                    sb.append("0");
                else
                    sb.append("1");
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binaryListToHexStringList(list);

//        String widthHexString = Integer
//                .toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8
//                        : (bmpWidth / 8 + 1));
//
//        if (widthHexString.length() > 2) {
//            Debug.log("decodeBitmap error. width is too large");
//            return null;
//        } else if (widthHexString.length() == 1) {
//            widthHexString = "0" + widthHexString;
//        }
//        widthHexString = widthHexString + "00";
//
//        String heightHexString = Integer.toHexString(bmpHeight);
//
//        if (heightHexString.length() > 2) {
//            Debug.log("decodeBitmap error. height is too large");
//            return null;
//        } else if (heightHexString.length() == 1) {
//            heightHexString = "0" + heightHexString;
//        }
//        heightHexString = heightHexString + "00";
        String widthHexString = String.format("%02x00", bmpWidth % 8 == 0 ? bmpWidth / 8 : (bmpWidth / 8 + 1));
        String heightHexString = String.format("%04x", bmpHeight);
        heightHexString = toLittleEndian(heightHexString);
        String normalMode = "00";
        String commandHexString = "1D7630" + normalMode;
        List<String> commandList = new ArrayList<>();
        commandList.add(commandHexString+widthHexString+heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    static String toLittleEndian(String bigEndian) {
        String littleEndian = "";
        for (int i = bigEndian.length() - 2; i >= 0; i -= 2) {
            littleEndian += bigEndian.substring(i, i + 2);
        }
        return littleEndian;
    }

    private static List<String> binaryListToHexStringList(List<String> list) {
        List<String> hexList = new ArrayList<>();
        for (String binaryStr : list) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < binaryStr.length(); i += 8) {
                String str = binaryStr.substring(i, i + 8);

                String hexString = myBinaryStrToHexString(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;

    }

    private static String myBinaryStrToHexString(String binaryStr) {
        String hBin = binaryStr.substring(0, 4);
        String lBin = binaryStr.substring(4, 8);
        String hHex = String.format("%X", Integer.parseInt(hBin, 2));
        String lHex = String.format("%X", Integer.parseInt(lBin, 2));
        return hHex + lHex;
    }

    private static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<>();

        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        return sysCopy(commandList);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
