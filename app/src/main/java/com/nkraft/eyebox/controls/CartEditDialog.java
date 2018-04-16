package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.nkraft.eyebox.R;

public class CartEditDialog implements DialogInterface.OnClickListener, View.OnClickListener {

    public interface ClickListener {
        void onConfirmEdit(long orderId, boolean isDeleted, int newQuantity, boolean anyBrand);
    }

    private CartEditDialog.ClickListener clickListener;
    private AlertDialog dialog;
    private long orderId;

    public CartEditDialog(Context context, long orderId, CartEditDialog.ClickListener clickListener) {
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.add_to_cart)
                .setIcon(R.drawable.ic_question_mark)
                .setView(R.layout.dialog_edit_orders)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
        this.orderId = orderId;
        this.clickListener = clickListener;
        dialog.findViewById(R.id.deo_btn_remove_order).setOnClickListener(this);
    }

    public void show() {
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        clickListener.onConfirmEdit(orderId, true, 0, false);
        dialog.dismiss();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            try {
                EditText editText = dialog.findViewById(R.id.deo_edit_quantity);
                CheckBox checkBox = dialog.findViewById(R.id.deo_chk_any_branch);
                String strQuantity = editText.getText().toString();
                boolean anyBrand = checkBox.isChecked();
                int newQuantity = Integer.parseInt(strQuantity);
                clickListener.onConfirmEdit(orderId, false, newQuantity, anyBrand);
            }
            catch (NumberFormatException e) {

            }
        }
    }

}
