package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Product;

import java.util.List;

public class ProductsAdapter extends BaseListAdapter<ProductsAdapter.ViewHolder, Product> {

    public ProductsAdapter(List<Product> dataList) {
        super(dataList, R.layout.row_product);
    }

    @Override
    void onDataBind(ViewHolder holder, Product data) {
        holder.name.setText(data.getName());
        holder.subName.setText(data.getGenericName());
        holder.price.setText("PHP " + formatCurrency(data.getPrice()));
        holder.soh.setText(formatString("SOH: %d", data.getSoh()));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView subName;
        TextView price;
        TextView soh;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            subName = itemView.findViewById(R.id.product_sub_name);
            price = itemView.findViewById(R.id.product_price);
            soh = itemView.findViewById(R.id.product_soh);
        }
    }
}
