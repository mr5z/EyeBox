package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.nkraft.eyebox.R;

public class CartDialog implements DialogInterface.OnClickListener {

    public interface ClickListener {
        void onProceedAdd(int quantity);
    }

    private ClickListener clickListener;
    private AlertDialog dialog;

    public CartDialog(Context context, ClickListener clickListener) {
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.add_to_cart)
                .setIcon(R.drawable.ic_question_mark)
                .setView(R.layout.dialog_add_cart)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
        this.clickListener = clickListener;
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            try {
                EditText editText = dialog.findViewById(R.id.dac_edit_quantity);
                String strQuantity = editText.getText().toString();
                int quantity = Integer.parseInt(strQuantity);
                clickListener.onProceedAdd(quantity);
            }
            catch (NumberFormatException e) {

            }
        }
    }

}
