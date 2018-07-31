package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Order;

import java.util.List;

public class CartAdapter extends BaseListAdapter<CartAdapter.ViewHolder, Order> {

    public CartAdapter(List<Order> dataList) {
        super(dataList, R.layout.row_cart);
    }

    @Override
    void onDataBind(ViewHolder holder, Order data) {
        holder.txtProductName.setText(data.getProduct().getName());
        holder.txtGenericName.setText(data.getProduct().getGenericName());
        holder.txtQuantity.setText(formatString("Item(s) (%d)", data.getQuantity()));
        holder.txtAnyBrand.setText(data.isAnyBrand() ? "Any Brand" : "");
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName;
        TextView txtGenericName;
        TextView txtQuantity;
        TextView txtAnyBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.vc_row_product_name);
            txtGenericName = itemView.findViewById(R.id.vc_row_generic_name);
            txtQuantity = itemView.findViewById(R.id.vc_row_quantity);
            txtAnyBrand = itemView.findViewById(R.id.txt_vc_row_product_any_brand);
        }
    }
}
