package com.nkraft.eyebox.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.BitmapCompat;
import android.view.View;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.controls.SketchView;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.models.Visit;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.utils.Debug;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SignatureActivity extends BaseActivity {

    interface FileWriteListener {
        void onFinish(boolean success);
    }

    private static final String SIGNATURE_FILE_TYPE = "png";

    @BindView(R.id.as_sketch_view)
    SketchView sketchView;

    @OnClick(R.id.as_fab_clear)
    void onClearClick(View view) {
        sketchView.clear();
    }

    @OnClick(R.id.as_fab_save)
    void onSaveClick(View view) {
        Bitmap bitmap = sketchView.bitmap();
        saveSignatureAsync(bitmap, (success) -> runOnUiThread(() -> {
            if (success) {
                popToRoot(VisitsActivity.class);
            }
            else {
                showSnackbar("Failed to save signature", "Retry", this::onSaveClick);
            }
        }));
    }

    void saveSignatureAsync(Bitmap bitmap, FileWriteListener fileWriteListener) {
        async(() -> {
            User currentUser = AccountService.instance().currentUser;
            Client client = getClient();
            String path = saveSignature(bitmap);
            Visit visit = new Visit();
            visit.setId((new Date().getTime() / 1000));
            visit.setDate((new Date()).getTime());
            visit.setFileName(path);
            visit.setAgent(currentUser.getId());
            visit.setCustomerId(client.getId());
            visit.setClientName(client.getName());
            visit.setFileSize(bitmap.getByteCount());
            visit.setFileWidth(bitmap.getWidth());
            visit.setFileHeight(bitmap.getHeight());
            visit.setFileType(SIGNATURE_FILE_TYPE);
            visit.setTime(new Date().getTime());
            database().visits().insertVisit(visit);
            fileWriteListener.onFinish(path != null);
        });
    }

    String saveSignature(Bitmap bitmap) {
        FileOutputStream outputStream;
        String fileName = (new Date()).getTime() + "." + SIGNATURE_FILE_TYPE;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            return getFileStreamPath(fileName).getAbsolutePath();
        }
        catch (Exception e) {
            Debug.log("error saving signature: %s", e.getMessage());
            return null;
        }
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
    }

    @Override
    int contentLayout() {
        return R.layout.activity_signature;
    }

    private Client getClient() {
        Intent intent = getIntent();
        return (Client) intent.getSerializableExtra("selectedClient");
    }

}
