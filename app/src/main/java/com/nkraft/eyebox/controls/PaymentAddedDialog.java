package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.nkraft.eyebox.R;


public class PaymentAddedDialog implements DialogInterface.OnClickListener {
    private View view;
    private AlertDialog dialog;

    public PaymentAddedDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_payment_added, null);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.payment_added)
                .setIcon(R.drawable.ic_check)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }
}
