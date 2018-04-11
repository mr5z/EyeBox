package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class PaymentAddedDialog implements DialogInterface.OnClickListener {
    private View view;
    private AlertDialog dialog;

    public PaymentAddedDialog(Context context) {
//        view = LayoutInflater.from(context).inflate(0, null);
//        dialog = new AlertDialog.Builder(context)
//                .setTitle(R.string.confirm_payment)
//                .setIcon(R.drawable.ic_question_mark)
//                .setView(view)
//                .setPositiveButton(android.R.string.ok, this)
//                .setNegativeButton(android.R.string.cancel, this)
//                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {

    }
}
