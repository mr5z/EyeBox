package com.nkraft.eyebox.controls;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.nkraft.eyebox.R;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class ConfirmPaymentDialog implements DialogInterface.OnClickListener {

    public interface ClickListener {
        void onConfirmTransaction(boolean includeExcess);
    }

    private View view;
    private AlertDialog dialog;
    private ClickListener clickListener;

    @SuppressLint("InflateParams")
    public ConfirmPaymentDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_payment, null);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.confirm_payment)
                .setIcon(R.drawable.ic_question_mark)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
//        CheckBox checkBox = view.findViewById(R.id.dcp_chk_excess);
        switch (which) {
            case BUTTON_POSITIVE:
                if (clickListener != null) {
                    clickListener.onConfirmTransaction(true/*checkBox.isChecked()*/);
                }
                break;
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void show() {
        dialog.show();
    }
}
