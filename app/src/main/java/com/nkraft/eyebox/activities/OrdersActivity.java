package com.nkraft.eyebox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.OrdersAdapter;
import com.nkraft.eyebox.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends ListActivity {

    private OrdersAdapter adapter;
    private List<Order> orders = new ArrayList<>();

    void initOrders() {
        async(() -> {
            List<Order> newOrders = database().orders().getAllOrders();
            orders.clear();
            orders.addAll(newOrders);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        initOrders();
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header(R.string.client));
        return headers;
    }

    @Override
    BaseListAdapter getAdapter() {
        adapter = new OrdersAdapter(orders);
        return adapter;
    }
}
