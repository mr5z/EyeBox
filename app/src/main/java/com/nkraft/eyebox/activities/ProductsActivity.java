package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ProductsAdapter;
import com.nkraft.eyebox.controls.dialogs.CartDialog;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Order;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ProductsActivity extends ListActivity<Product> implements
        TaskWrapper.Task<PagedResult<List<Product>>>,
        BaseListAdapter.ItemClickListener<Product>,
        CartDialog.ClickListener {

    public static final int REQUEST_MODIFIED_ORDERS = 1;

    Set<Order> cart = new HashSet<>();
    Product selectedProduct;

    private TaskWrapper<PagedResult<List<Product>>> productTask() {
        return new TaskWrapper<>(this);
    }

    private Client getSelectedClient() {
        Intent intent = getIntent();
        return (Client) intent.getSerializableExtra("selectedClient");
    }

    void goToViewCartActivity() {
        if (!cart.isEmpty()) {
            ArrayList<Order> orders = new ArrayList<>(cart);
            Intent intent = new Intent(this, ViewCartActivity.class);
            intent.putExtra("selectedClient", getSelectedClient());
            intent.putParcelableArrayListExtra("orders", orders);
            startActivityForResult(intent, REQUEST_MODIFIED_ORDERS);
        }
    }

    private void addToCart(Order order) {
        cart.add(order);
        updateCartButtonCount();
    }

    private void updateCartButtonCount() {
        Button button = findViewById(R.id.cart_button);
        button.setText(String.format(Locale.getDefault(), "CART (%d)", cart.size()));
    }

    void onUpdateOrders(ArrayList<Order> newOrders) {
        cart = new HashSet<>(newOrders);
        updateCartButtonCount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MODIFIED_ORDERS:
                ArrayList<Order> orders = data.getParcelableArrayListExtra("orders");
                onUpdateOrders(orders);
                break;
        }
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.select_product);
        productTask().execute();
    }

    @Override
    public void onRefresh() {
        productTask().execute();
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        Client client = getSelectedClient();
        headers.add(new Header(client.getName()));
        return headers;
    }

    @Override
    List<Footer> footerList() {
        List<Footer> footers = new ArrayList<>();
        Footer footer = new Footer("Cart (0)",
                R.id.cart_button,
                android.R.color.white,
                android.R.color.holo_blue_light,
                this::goToViewCartActivity);
        footers.add(footer);
        return footers;
    }

    @Override
    BaseListAdapter initializeAdapter() {
        ProductsAdapter adapter = new ProductsAdapter(getDataList());
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onTaskBegin() {
    }

    @Override
    public PagedResult<List<Product>> onTaskExecute() {
        List<Product> productList = database().products().getAllProducts();
        return new PagedResult<>(productList, productList.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<Product>> result) {
        if (result.isSuccess()) {
            assert result.data != null;
            setDataList(result.data);
            notifyDataSetChanged();
        }
        setRefreshing(false);
    }

    @Override
    public void onItemClick(Product data) {
        CartDialog dialog = new CartDialog(this, data, this);
        dialog.show();
        selectedProduct = data;
    }

    @Override
    public void onProceedAdd(int quantity) {
        if (quantity > 0) {
            Order.Product product = new Order.Product();
            product.setId(selectedProduct.getId());
            product.setName(selectedProduct.getName());
            product.setGenericName(selectedProduct.getGenericName());
            product.setUnit(selectedProduct.getUnits());
            addToCart(new Order(product, quantity));
        }
    }

    @Override
    String getSearchableFields(Product product) {
        return product.getName();
    }
}
