package com.nkraft.eyebox.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.services.PrinterService;
import com.nkraft.eyebox.utils.BitmapUtils;
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

    static final int DEFAULT_PREVIEW_FONT_SIZE = 24;

    private PrinterService printerService;
    private BluetoothDevice selectedDevice;
    private PrintMedium printMedium = PrintMedium.BLUETOOTH;
    private Bitmap cachedBitmapHeader;
    private Bitmap cachedBitmapFooter;

    @BindView(R.id.btn_show_device_list)
    Button btnShowDeviceList;

    @BindView(R.id.ap_webview_header)
    WebView webViewHeader;

    @BindView(R.id.ap_webview_footer)
    WebView webViewFooter;

    @BindView(R.id.ap_linearlayout_body)
    LinearLayout linearLayoutBody;

    @Override
    int contentLayout() {
        return R.layout.activity_print;
    }

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
        printerService = PrinterService.instance();
        setPageTitle(R.string.select_printer);
        PrintTemplate printTemplate = getSelectedTemplate();
        configureWebViews();
        setPreviewHeader(printTemplate.getHtmlHeader());
        setPreviewBody(printTemplate.getDataLines());
        setPreviewFooter(printTemplate.getHtmlFooter());
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

    private void configureWebViews() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
        webViewHeader.getSettings().setDefaultFontSize(DEFAULT_PREVIEW_FONT_SIZE);
        webViewFooter.getSettings().setDefaultFontSize(DEFAULT_PREVIEW_FONT_SIZE);

        webViewHeader.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webViewHeader.setDrawingCacheEnabled(true);
        webViewHeader.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        webViewHeader.buildDrawingCache();

        webViewFooter.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webViewFooter.setDrawingCacheEnabled(true);
        webViewFooter.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        webViewFooter.buildDrawingCache();
    }

    private void setPreviewHeader(String htmlString) {
        webViewHeader.loadData(htmlString, "text/html; charset=utf-8", "utf-8");
    }

    private void setPreviewFooter(String htmlString) {
        webViewFooter.loadData(htmlString, "text/html; charset=utf-8", "utf-8");
    }

    private void setPreviewBody(List<String> lines) {
        for (String line : lines) {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_PREVIEW_FONT_SIZE);
            textView.setText(line);
            linearLayoutBody.addView(textView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private Bitmap getHeaderBitmap() {
        return convertToBitmap(webViewHeader);
    }

    private Bitmap getFooterBitmap() {
        return convertToBitmap(webViewFooter);
    }

    private Bitmap convertToBitmap(WebView webView) {
        int width = webView.getWidth();
        int height = webView.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
        Bitmap cacheBitmap = webView.getDrawingCache();
        return isEmptyBitmap(bitmap) ? cacheBitmap : bitmap;
    }

    private void performPrintAsync() {
        async(() -> {
            PrintTemplate printTemplate = getSelectedTemplate();
            if (printTemplate == null) {
                runOnUiThread(() -> showSnackbar("No template selected"));
                Debug.log("No template selected");
                return;
            }

            // NOTE setting this to 45 and above makes the printer memory corrupted
            // on the succeeding prints
            final int widthInMillimeter = 44 * 8;
            final int heightInPixel = 255;
            List<PrintTemplate.Data> printData = printTemplate.getPrintData();
            Bitmap header = BitmapUtils.resizeBitmapByHeight(getHeaderBitmap(), heightInPixel);
            Bitmap footer = BitmapUtils.resizeBitmapByHeight(getFooterBitmap(), heightInPixel);
            if (header != null && !isEmptyBitmap(header)) {
                printerService.printImage(header);
            }
            for(int i = 0;i < printData.size(); ++i) {
                PrintTemplate.Data data = printData.get(i);
                String line = data.getLine();
                Bitmap image = data.getImage();
                if (line != null) {
                    printerService.printText(line, data.getFontStyle(), data.getAlignment());
                }
                if (image != null) {
                    printerService.printImage(image);
                }
            }
            if (footer != null && !isEmptyBitmap(footer)) {
                printerService.printImage(footer);
            }
            printerService.printNewLine();
            printerService.printNewLine();
            printerService.printNewLine();
            printerService.flush();
            if (header != null)
                header.recycle();
            if (footer != null)
                footer.recycle();
        });
    }

    boolean isEmptyBitmap(Bitmap bitmap) {
        Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        return emptyBitmap.sameAs(bitmap);
    }

    private PrintTemplate getSelectedTemplate() {
        Intent data = getIntent();
       return (PrintTemplate) data.getParcelableExtra("selectedTemplate");
    }

    private Bitmap toBitmap(byte[] pixels) {
        if (pixels == null)
            return null;
        return BitmapFactory.decodeByteArray(pixels, 0, pixels.length);
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        float aspectRatio = bitmap.getWidth() /
                (float) bitmap.getHeight();
        int width = 255;
        int height = Math.round(width / aspectRatio);

        return Bitmap.createScaledBitmap(
                bitmap, width, height, false);
    }

    private byte[] toByteArray(String htmlString) {
        int width = 128;
        WebView webView = new WebView(this);
        webView.loadData(htmlString, "text/html", "UTF-8");
        // set the correct height of the webview and do measure and layout using it before taking the screenshot
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(webView.getContentHeight(), View.MeasureSpec.EXACTLY);
        webView.measure(widthMeasureSpec, heightMeasureSpec);
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(width, 64, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
        return toByteArray(bitmap);
    }

    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
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

        builder.setItems(deviceNames, (dialog, which) -> onDeviceSelect(deviceList.get(which)));

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

}
