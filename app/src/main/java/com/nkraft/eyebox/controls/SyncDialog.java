package com.nkraft.eyebox.controls;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.nkraft.eyebox.R;

public class SyncDialog implements View.OnClickListener {

    public interface SyncListener {
        void onSync(int processTypes);
    }

    private final View view;
    private AlertDialog dialog;
    private SyncListener syncListener;

    @SuppressLint("InflateParams")
    public SyncDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_sync, null);
        view.findViewById(R.id.ds_btn_sync).setOnClickListener(this);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.select_module_to_sync)
                .setView(view)
                .create();
    }

    @Override
    public void onClick(View view) {
        if (syncListener != null) {
            int processTypes = getProcessTypes();
            syncListener.onSync(processTypes);
        }
        dismiss();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setSyncListener(SyncListener syncListener) {
        this.syncListener = syncListener;
    }

    private int getProcessTypes() {
        LinearLayout layout = (LinearLayout) view;
        int flags = 0;
        for(int i = 0;i < layout.getChildCount(); ++i) {
            View view = layout.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    int tag = Integer.parseInt((String) checkBox.getTag());
                    flags |= tag;
                }
            }
        }
        return flags;
    }
}
