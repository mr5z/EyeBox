package com.nkraft.eyebox.services;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;

import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.PrinterCommands;
import com.nkraft.eyebox.utils.PrinterUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrinterService {

    private static PrinterService _instance;

    private BluetoothSocket deviceSocket;

    public static PrinterService instance() {
        if (_instance == null) {
            _instance = new PrinterService();
        }
        return _instance;
    }

    public void connect(BluetoothDevice device) throws
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IOException {
        Method m = device.getClass().getMethod("createRfcommSocket", Integer.TYPE);
        deviceSocket = (BluetoothSocket)m.invoke(device, 1);
        deviceSocket.connect();
    }

    public void disconnect() {
        if (deviceSocket != null && deviceSocket.isConnected()) {
            try {
                deviceSocket.close();
            }
            catch (IOException e) {
                Debug.log("Error closing socket connection: %s", e.getMessage());
            }
        }
    }

    private void write(byte[] data) throws IOException {
        deviceSocket.getOutputStream().write(data);
    }

    private void write(byte data) throws IOException {
        deviceSocket.getOutputStream().write(data);
    }

    public void flush() {
        try {
            deviceSocket.getOutputStream().flush();
        } catch (IOException e) {
            Debug.log("Error flushing stream: %s", e.getMessage());
        }
    }

    public void printText(String message, FontStyle fontStyle, TextAlignment textAlignment) {
        byte[] normal       = new byte[] { 0x1B,0x21,0x03 }; // 0 - normal size text
        byte[] bold         = new byte[] { 0x1B,0x21,0x08 }; // 1 - only bold text
        byte[] boldMedium   = new byte[] { 0x1B,0x21,0x20 }; // 2 - bold with medium text
        byte[] boldLarge    = new byte[] { 0x1B,0x21,0x10 }; // 3 - bold with large text
        try {
            switch (fontStyle) {
                case NORMAL:
                    write(normal);
                    break;
                case BOLD:
                    write(bold);
                    break;
                case BOLD_MEDIUM:
                    write(boldMedium);
                    break;
                case BOLD_LARGE:
                    write(boldLarge);
                    break;
            }
            switch (textAlignment) {
                case LEFT:
                    //left align
                    write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case CENTER:
                    //center align
                    write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case RIGHT:
                    //right align
                    write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            write(message.getBytes());
            write(PrinterCommands.LF);
        }
        catch (IOException e) {
            Debug.log("Error: %s", e.getMessage());
        }
    }

    //print photo
    public void printImage(Bitmap bitmap) {
        if(bitmap != null){
            byte[] command = PrinterUtils.decodeBitmap(bitmap);
            try {
                write(PrinterCommands.ESC_ALIGN_CENTER);
                printBytes(command);
            } catch (IOException e) {
                Debug.log("Error printing image: %s", e.getMessage());
            }
        }
        else{
            Debug.log("Print Photo error. the file doesn't exist");
        }
    }

    //print unicode
    public void printUnicode() throws IOException {
        write(PrinterCommands.ESC_ALIGN_CENTER);
        printBytes(PrinterUtils.UNICODE_TEXT);
    }

    //print new line
    public void printNewLine() {
        try {
            write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            Debug.log("Error printing new line: %s", e.getMessage());
        }
    }

    public void resetPrint() throws IOException {
        write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
        write(PrinterCommands.FS_FONT_ALIGN);
        write(PrinterCommands.ESC_ALIGN_LEFT);
        write(PrinterCommands.ESC_CANCEL_BOLD);
        write(PrinterCommands.LF);
    }

    public void printText(String msg) {
        printBytes(msg.getBytes());
    }

    private void printBytes(byte[] msg) {
        try {
            write(msg);
        } catch (IOException e) {
            Debug.log("printBytes: %s", e.getMessage());
        }
    }



}
