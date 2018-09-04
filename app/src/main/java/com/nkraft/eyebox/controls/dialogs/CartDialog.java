package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.utils.Formatter;

public class CartDialog extends BaseDialog<Product> {

    public interface ClickListener {
        void onProceedAdd(int quantity, boolean anyBrand);
    }

    private ClickListener clickListener;

    public CartDialog(Context context, @NonNull Product data, @NonNull ClickListener clickListener) {
        super(context, data, true);
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreateView(View view, Product data) {
        TextView txtProductName = view.findViewById(R.id.dac_txt_produt_name);
        TextView txtGenericName = view.findViewById(R.id.dac_txt_generic_name);
        TextView txtUnitName = view.findViewById(R.id.dac_txt_unit);
        TextView txtByx = view.findViewById(R.id.dac_txt_byx);
        txtProductName.setText(data.getName());
        txtGenericName.setText(data.getGenericName());
        txtUnitName.setText(data.getUnits());
        txtByx.setText(Formatter.string("Pkg: %d", (int)data.getByX()));
        setOnClickListener((p) -> {
            EditText editText = view.findViewById(R.id.dac_edit_quantity);
            CheckBox checkBox = view.findViewById(R.id.dac_chk_any_brand);
            String strQuantity = editText.getText().toString();
            int quantity = Integer.parseInt("0" + strQuantity);
            boolean anyBrand = checkBox.isChecked();
            clickListener.onProceedAdd(quantity, anyBrand);
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
