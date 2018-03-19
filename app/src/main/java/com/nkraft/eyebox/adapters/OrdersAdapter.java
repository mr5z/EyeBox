package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Order;

import java.util.List;

public class OrdersAdapter extends BaseListAdapter<OrdersAdapter.ViewHolder, Order> {

    public OrdersAdapter(List<Order> dataList) {
        super(dataList, R.layout.order_row);
    }

    @Override
    void onDataBind(ViewHolder holder, Order data) {

    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
