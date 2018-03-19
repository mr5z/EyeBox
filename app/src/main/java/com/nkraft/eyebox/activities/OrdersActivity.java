package com.nkraft.eyebox.activities;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.OrdersAdapter;
import com.nkraft.eyebox.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends ListActivity {

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header(R.string.client));
        return headers;
    }

    @Override
    BaseListAdapter getAdapter() {
        List<Order> orders = new ArrayList<>();
        return new OrdersAdapter(orders);
    }
}
