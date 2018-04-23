package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Order;

import java.util.List;

public class OrdersAdapter extends BaseListAdapter<OrdersAdapter.ViewHolder, Order> {

    public OrdersAdapter(List<Order> dataList) {
        super(dataList, R.layout.row_order);
    }

    @Override
    void onDataBind(ViewHolder holder, Order data) {
        Order.Product product = data.getProduct();
        holder.txtProductName.setText(product.getName());
        holder.txtGenericName.setText(product.getGenericName());
        holder.txtQuantity.setText(formatString("Quantity ( %d )", data.getQuantity()));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName;
        TextView txtGenericName;
        TextView txtQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.vc_row_product_name);
            txtGenericName = itemView.findViewById(R.id.vc_row_generic_name);
            txtQuantity = itemView.findViewById(R.id.vc_row_quantity);
        }
    }
}
