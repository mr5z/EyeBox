package com.nkraft.eyebox.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Order;

public class CartEditDialog implements DialogInterface.OnClickListener, View.OnClickListener {

    public interface ClickListener {
        void onConfirmEdit(long orderId, boolean isDeleted, int newQuantity, boolean anyBrand);
    }

    private CartEditDialog.ClickListener clickListener;
    private AlertDialog dialog;
    private long orderId;

    public CartEditDialog(Context context, Order order, CartEditDialog.ClickListener clickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_orders, null);
        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
        orderId = order.getId();
        this.clickListener = clickListener;

        Button button = view.findViewById(R.id.deo_btn_remove_order);
        TextView txtProductName = view.findViewById(R.id.deo_txt_product_name);
        EditText editQuantity = view.findViewById(R.id.deo_edit_quantity);
        TextView txtUnit = view.findViewById(R.id.deo_txt_unit);

        button.setOnClickListener(this);
        editQuantity.setText(String.valueOf(order.getQuantity()));
        txtProductName.setText(order.getProduct().getName());
        txtUnit.setText(order.getProduct().getUnit());
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
