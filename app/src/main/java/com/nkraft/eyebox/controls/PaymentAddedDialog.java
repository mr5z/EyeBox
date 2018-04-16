package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.nkraft.eyebox.R;


public class PaymentAddedDialog implements DialogInterface.OnClickListener {

    public interface ClickListener {
        void onConfirmPayment();
    }

    private ClickListener clickListener;
    private View view;
    private AlertDialog dialog;

    public PaymentAddedDialog(Context context, ClickListener clickListener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_payment_added, null);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.payment_added)
                .setIcon(R.drawable.ic_check)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .create();
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            clickListener.onConfirmPayment();
        }
    }

    public void show() {
        dialog.show();
    }
}
