package com.nkraft.eyebox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.OrdersAdapter;
import com.nkraft.eyebox.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends ListActivity<Order> {

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
        return new OrdersAdapter(getDataList());
    }

    @Override
    String getSearchableFields(Order order) {
        return order.getClientName();
    }
}
