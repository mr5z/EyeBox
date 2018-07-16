package com.nkraft.eyebox.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.os.SystemClock;

import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.PrinterCommands;
import com.nkraft.eyebox.utils.PrinterUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.UUID;

public class PrinterService {

    private static PrinterService _instance;

    private BluetoothSocket deviceSocket;

    public static PrinterService instance() {
        if (_instance == null) {
            _instance = new PrinterService();
        }
        return _instance;
    }

    public boolean connect(BluetoothDevice device) throws IOException {
        try {
            deviceSocket = getBluetoothSocket1(device);
        }
        catch (IOException e) {
            // fallback
            try {
                deviceSocket = getBluetoothSocket2(device);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                Debug.log(e1.getMessage());
            }
        }
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        deviceSocket.connect();
        return deviceSocket.isConnected();
    }

    private BluetoothSocket getBluetoothSocket1(BluetoothDevice device) throws IOException {
        UUID uuid = device.getUuids()[0].getUuid();
        return device.createRfcommSocketToServiceRecord(uuid);
    }

    private BluetoothSocket getBluetoothSocket2(BluetoothDevice device) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        //noinspection JavaReflectionMemberAccess
        Method m = device.getClass().getMethod("createRfcommSocket", Integer.TYPE);
        return (BluetoothSocket)m.invoke(device, 1);
    }

    public void disconnect() {
        if (deviceSocket != null && deviceSocket.isConnected()) {
            try {
                closeOutputStream();
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

    private void closeOutputStream() throws IOException {
        deviceSocket.getOutputStream().close();
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
            write(PrinterCommands.FEED_LINE);
        }
        catch (IOException e) {
            Debug.log("Error: %s", e.getMessage());
        }
    }

    @Deprecated
    public void printImageDeprecated(Bitmap bitmap) {
        if(bitmap != null) {
            byte[] command = PrinterUtils.decodeBitmap(bitmap);
            if (command == null) {
                Debug.log("Error printing. Invalid image dimension");
                return;
            }
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

    public void printImage(Bitmap image) {
        try {
            BitSet imageBits = getBitsImageData(image);

            byte widthLSB = (byte)(image.getWidth() & 0xFF);
            byte widthMSB = (byte)((image.getWidth() >> 8) & 0xFF);

            // COMMANDS
            byte[] selectBitImageModeCommand = buildPosCommand(
                    PrinterCommands.SELECT_BIT_IMAGE_MODE, (byte) 0x21, widthLSB, widthMSB);
            byte[] setLineSpacing24Dots = buildPosCommand(
                    PrinterCommands.SET_LINE_SPACING, (byte) 24);
            byte[] setLineSpacing30Dots = buildPosCommand(
                    PrinterCommands.SET_LINE_SPACING, (byte) 30);

            write(PrinterCommands.INITIALIZE_PRINTER);
            write(setLineSpacing24Dots);

            int offset = 0;
            while (offset < image.getHeight()) {
                write(selectBitImageModeCommand);

                int imageDataLineIndex = 0;
                byte[] imageDataLine = new byte[3 * image.getWidth()];

                for (int x = 0; x < image.getWidth(); ++x) {

                    // Remember, 24 dots = 24 bits = 3 bytes.
                    // The 'k' variable keeps track of which of those
                    // three bytes that we're currently scribbling into.
                    for (int k = 0; k < 3; ++k) {
                        byte slice = 0;

                        // A byte is 8 bits. The 'b' variable keeps track
                        // of which bit in the byte we're recording.
                        for (int b = 0; b < 8; ++b) {
                            // Calculate the y position that we're currently
                            // trying to draw. We take our offset, divide it
                            // by 8 so we're talking about the y offset in
                            // terms of bytes, add our current 'k' byte
                            // offset to that, multiple by 8 to get it in terms
                            // of bits again, and add our bit offset to it.
                            int y = (((offset / 8) + k) * 8) + b;

                            // Calculate the location of the pixel we want in the bit array.
                            // It'll be at (y * width) + x.
                            int i = (y * image.getWidth()) + x;

                            // If the image (or this stripe of the image)
                            // is shorter than 24 dots, pad with zero.
                            boolean v = false;
                            if (i < imageBits.length()) {
                                v = imageBits.get(i);
                            }
                            // Finally, store our bit in the byte that we're currently
                            // scribbling to. Our current 'b' is actually the exact
                            // opposite of where we want it to be in the byte, so
                            // subtract it from 7, shift our bit into place in a temp
                            // byte, and OR it with the target byte to get it into there.
                            slice |= (byte) ((v ? 1 : 0) << (7 - b));
                        }

                        imageDataLine[imageDataLineIndex + k] = slice;

                        // Phew! Write the damn byte to the buffer
                        //printOutput.write(slice);
                    }

                    imageDataLineIndex += 3;
                }
                write(imageDataLine);
                offset += 24;
                SystemClock.sleep(10);
                write(PrinterCommands.PRINT_AND_FEED_PAPER);
            }

            write(setLineSpacing30Dots);
        } catch (IOException ex) {
            Debug.log("IOException: %s", ex.getMessage());
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
            write(PrinterCommands.LF);
        } catch (IOException e) {
            Debug.log("Error printing new line: %s", e.getMessage());
        }
    }

    public void printNewLine(byte lineCount) {
        try {
            write(new byte[] { 0x1b, 0x64, lineCount });
        }
        catch (IOException e) {
            Debug.log("Error printing new line(%d): %s", lineCount, e.getMessage());
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

    private byte[] buildPosCommand(byte[] command, byte... args) {
        byte[] posCommand = new byte[command.length + args.length];
        System.arraycopy(command, 0, posCommand, 0, command.length);
        System.arraycopy(args, 0, posCommand, command.length, args.length);
        return posCommand;
    }

    private BitSet getBitsImageData(Bitmap image) {
        int threshold = 127;
        int index = 0;
        int dimension = image.getWidth() * image.getHeight();
        BitSet imageBitsData = new BitSet(dimension);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getPixel(x, y);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                int luminance = (int)(red * 0.3 + green * 0.59 + blue * 0.11);
                imageBitsData.set(index, (luminance < threshold));
                index++;
            }
        }

        return imageBitsData;
    }

}
