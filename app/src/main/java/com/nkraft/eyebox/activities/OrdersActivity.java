package com.nkraft.eyebox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.OrdersAdapter;
import com.nkraft.eyebox.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends ListActivity<Order> implements BaseListAdapter.LongItemClickListener<Order> {

    void initOrders() {
        async(() -> {
            List<Order> newOrders = database().orders().getAllOrders();
            setDataList(newOrders);
            runOnUiThread(this::notifyDataSetChanged);
        });
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.orders);
        initOrders();
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header(R.string.client));
        return headers;
    }

    @Override
    BaseListAdapter initializeAdapter() {
        OrdersAdapter adapter = new OrdersAdapter(getDataList());
        adapter.setOnLongClickListener(this);
        return adapter;
    }

    @Override
    String[] getSearchableFields(Order order) {
        return new String[] { order.getClientName(), order.getProduct().getName() };
    }

    @Override
    public boolean onLongItemClick(Order data) {
        showConfirmDialog(() -> async(() -> {
            long affectedRows = database().orders().deleteById(data.getId());
            affectedRows += database().orders2().deleteById(data.getId());
            if (affectedRows > 0) {
                runOnUiThread(() -> {
                    if (removeData(data)) {
                        notifyDataSetChanged();
                    }
                });
            }
        }), "Are you sure you want to delete this order?");
        return true;
    }
}
