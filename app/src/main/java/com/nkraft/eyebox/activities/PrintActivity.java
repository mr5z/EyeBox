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
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.services.FontStyle;
import com.nkraft.eyebox.services.PrinterService;
import com.nkraft.eyebox.services.TextAlignment;
import com.nkraft.eyebox.utils.Debug;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class PrintActivity extends BaseActivity {

    private interface ConnectionListener {
        void onConnect(boolean success);
    }

    enum PrintMedium {
        BLUETOOTH,
        USB,
        WIFI
    }

    static final int REQUEST_ENABLE_BT = 1;

    PrinterService printerService;
    BluetoothDevice selectedDevice;
    PrintMedium printMedium = PrintMedium.BLUETOOTH;

    @BindView(R.id.btn_show_device_list)
    Button btnShowDeviceList;

    @OnCheckedChanged({R.id.ap_radio_bluetooth, R.id.ap_radio_usb, R.id.ap_radio_wifi})
    void onSwitchPrintMedia(CompoundButton compoundButton, boolean checked) {
        if (!checked)
            return;

        switch (compoundButton.getId()) {
            case R.id.ap_radio_bluetooth:
                printMedium = PrintMedium.BLUETOOTH;
                btnShowDeviceList.setText("Opt BT Device");
                break;
            case R.id.ap_radio_usb:
                printMedium = PrintMedium.USB;
                btnShowDeviceList.setText("Opt USB Device");
                break;
            case R.id.ap_radio_wifi:
                printMedium = PrintMedium.WIFI;
                btnShowDeviceList.setText("Opt WiFi Device");
                break;
        }
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.select_printer);
        printerService = PrinterService.instance();
    }

    @OnClick(R.id.btn_print)
    void onPrintClick(View view) {
        if (printMedium != PrintMedium.BLUETOOTH) {
            showAlertDialog("Unsupported Operation", "Print medium not yet supported");
            return;
        }

        if (selectedDevice == null) {
            showAlertDialog("Error", "No selected device!");
            return;
        }

        performPrintAsync();
    }

    private void performPrintAsync() {
        async(() -> {
            PrintTemplate printTemplate = getSelectedTemplate();
            if (printTemplate == null) {
                runOnUiThread(() -> showSnackbar("No template selected"));
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

    private PrintTemplate getSelectedTemplate() {
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
        if (printMedium != PrintMedium.BLUETOOTH) {
            showAlertDialog("Unsupported Operation", "Print medium not yet supported");
            return;
        }

        if (selectedDevice == null) {
            showAlertDialog("Error", "No selected device!");
            return;
        }

        showToast("Connecting...");

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
        if (printMedium != PrintMedium.BLUETOOTH) {
            showAlertDialog("Unsupported Operation", "Print medium not yet supported");
            return;
        }

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

        async(() -> {
            try {
                printerService.disconnect();
                printerService.connect(selectedDevice);
                connectionListener.onConnect(true);
            }
            catch (NoSuchMethodException |
                   InvocationTargetException |
                   IllegalAccessException |
                   IOException e) {
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
