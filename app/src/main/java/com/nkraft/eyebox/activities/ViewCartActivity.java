package com.nkraft.eyebox.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.OrdersAdapter;
import com.nkraft.eyebox.controls.CartEditDialog;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ViewCartActivity extends ListActivity<Order> implements
        BaseListAdapter.ItemClickListener<Order>,
        CartEditDialog.ClickListener {

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.view_cart);
    }

    @Override
    BaseListAdapter initializeAdapter() {
        setDataList(getOrders());
        OrdersAdapter adapter = new OrdersAdapter(getDataList());
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    List<Footer> footerList() {
        List<Footer> footers = new ArrayList<>();
        Footer footer = new Footer(getString(R.string.send_order),
                R.id.order_button,
                android.R.color.white,
                android.R.color.holo_blue_light,
                this::sendOrders);
        footers.add(footer);
        return footers;
    }

    @Override
    String[] getSearchableFields(Order order) {
        return new String[] { order.getClientName() };
    }

    void sendOrders() {
        async(() -> {
            Client client = getSelectedClient();
            List<Order> orderList = getDataList();
            for (Order order : orderList) {
                order.setClientName(client.getName());
                order.setDateOrdered((new Date()).getTime());
            }
            database().orders().insertOrders(orderList);
            runOnUiThread(this::popToMainActivity);
        });
    }

    private ArrayList<Order> getOrders() {
        Intent intent = getIntent();
        return intent.getParcelableArrayListExtra("orders");
    }

    private Client getSelectedClient() {
        Intent intent = getIntent();
        return (Client) intent.getSerializableExtra("selectedClient");
    }

    @Override
    public void onItemClick(Order data) {
        CartEditDialog dialog = new CartEditDialog(this, data, this);
        dialog.show();
    }

    @Override
    public void onConfirmEdit(long orderId, boolean isDeleted, int newQuantity, boolean anyBrand) {
        if (isDeleted) {
            deleteOrder(orderId);
            if (isDataListEmpty()) {
                goBack();
            }
        }
        else {
            for (Order order : getDataList()) {
                if (order.getId() == orderId) {
                    order.setQuantity(newQuantity);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent intent = new Intent();
        ArrayList<Order> orders = (ArrayList<Order>) getDataList();
        intent.putParcelableArrayListExtra("orders", orders);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    void deleteOrder(long orderId) {
        for (Iterator<Order> iterator  = getDataList().iterator(); iterator.hasNext(); ) {
            Order order = iterator.next();
            if (order.getId() == orderId) {
                iterator.remove();
                notifyDataSetChanged();
                break;
            }
        }
    }
}
