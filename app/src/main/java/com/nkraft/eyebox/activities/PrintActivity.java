package com.nkraft.eyebox.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.services.FontStyle;
import com.nkraft.eyebox.services.PrinterService;
import com.nkraft.eyebox.services.TextAlignment;
import com.nkraft.eyebox.utils.Debug;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PrintActivity extends BaseActivity {

    private interface ConnectionListener {
        void onConnect(boolean success);
    }

    static final int REQUEST_ENABLE_BT = 1;

    PrinterService printerService;
    BluetoothDevice selectedDevice;

    @BindView(R.id.btn_show_device_list)
    Button btnShowDeviceList;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        printerService = PrinterService.instance();
    }

    @OnClick(R.id.btn_print)
    void onPrintClick(View view) {
        async(() -> {
            PrintTemplate printTemplate = getSelectedTemplate();
            if (printTemplate == null) {
                showSnackbar("No template selected");
                Debug.log("No template selected");
                return;
            }

            List<PrintTemplate.Data> printData = printTemplate.getPrintData();
            for(int i = 0;i < printData.size(); ++i) {
                PrintTemplate.Data data = printData.get(i);
                String line = data.getLine();
                Bitmap image = data.getImage();
                if (line != null) {
                    printerService.printText(line, FontStyle.BOLD, data.getAlignment());
                }
                if (image != null) {
                    printerService.printImage(image);
                }
            }
            printerService.printNewLine();
            printerService.printNewLine();
            printerService.printNewLine();
            printerService.flush();
        });
    }

    PrintTemplate getSelectedTemplate() {
        Intent data = getIntent();
       return (PrintTemplate) data.getParcelableExtra("selectedTemplate");
    }

    byte[] drawableToBytes(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @OnClick(R.id.btn_bluetooth_connect)
    void onConnectClick(View view) {
        asyncConnect(success -> runOnUiThread(() -> {
            if (success) {
                showSnackbar("Connected to: %s", selectedDevice.getName());
            }
            else {
                showSnackbar("Failed to connect to device: %s", selectedDevice.getName());
            }
        }));
    }

    @OnClick(R.id.btn_show_device_list)
    void onShowDeviceListClick() {

        if (!isBluetoothEnabled()) {
            requestBluetoothEnabled();
            return;
        }

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a device");

        List<BluetoothDevice> deviceList = getPairedDevices();

        String[] deviceNames = new String[deviceList.size()];
        for(int i = 0;i < deviceList.size(); ++i) {
            BluetoothDevice device = deviceList.get(i);
            deviceNames[i] = device.getName();
        }

        builder.setItems(deviceNames, (dialog, which) -> {
            onDeviceSelect(deviceList.get(which));
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void asyncConnect(ConnectionListener connectionListener) {

        if (selectedDevice == null) {
            showSnackbar("No selected device");
        }

        async(() -> {
            try {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//                boolean status = BluetoothPrintDriver.open(adapter, selectedDevice);
                try {
                    printerService.connect(selectedDevice);
                    connectionListener.onConnect(true);
                }
                catch (Exception e) {
                    connectionListener.onConnect(false);
                }
            }
            catch (Exception ex) {
                connectionListener.onConnect(false);
            }
        });
    }

    void onDeviceSelect(BluetoothDevice selectedDevice) {
        closePreviousConnection();
        btnShowDeviceList.setText(selectedDevice.getName());
        this.selectedDevice = selectedDevice;
    }

    void closePreviousConnection() {
        if (selectedDevice != null) {
            printerService.disconnect();
        }
    }

    List<BluetoothDevice> getPairedDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return new ArrayList<>(adapter.getBondedDevices());
    }

    boolean isBluetoothEnabled() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }

    void requestBluetoothEnabled() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                break;
        }
    }

    @Override
    int contentLayout() {
        return R.layout.activity_print;
    }

}
