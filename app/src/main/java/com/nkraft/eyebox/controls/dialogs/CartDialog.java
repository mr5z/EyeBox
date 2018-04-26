package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Product;

public class CartDialog extends BaseDialog<Product> {

    public interface ClickListener {
        void onProceedAdd(int quantity);
    }

    private ClickListener clickListener;

    public CartDialog(Context context, @NonNull Product data, @NonNull ClickListener clickListener) {
        super(context, data, true);
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreateView(View view, Product data) {
        TextView txtProductName = view.findViewById(R.id.dac_txt_produt_name);
        TextView txtUnitName = view.findViewById(R.id.dac_txt_unit);
        txtProductName.setText(data.getName());
        txtUnitName.setText(data.getUnits());
        setOnClickListener((p) -> {
            EditText editText = view.findViewById(R.id.dac_edit_quantity);
            String strQuantity = editText.getText().toString();
            int quantity = Integer.parseInt("0" + strQuantity);
            clickListener.onProceedAdd(quantity);
        });
    }

    @Override
    protected int layout() {
        return R.layout.dialog_add_cart;
    }

    @Override
    protected int icon() {
        return R.drawable.ic_question_mark;
    }

    @Override
    protected int title() {
        return R.string.add_to_cart;
    }

}
