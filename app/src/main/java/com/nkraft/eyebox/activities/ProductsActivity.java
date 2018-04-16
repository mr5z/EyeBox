package com.nkraft.eyebox.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ProductsAdapter;
import com.nkraft.eyebox.controls.CartDialog;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Order;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ProductsActivity extends ListActivity implements
        TaskWrapper.Task<PagedResult<List<Product>>>,
        BaseListAdapter.ItemClickListener<Product>,
        CartDialog.ClickListener {

    ProductsAdapter adapter;
    List<Product> products = new ArrayList<>();
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
            startActivity(intent);
        }
    }

    void addToCart(Order order) {
        cart.add(order);
        @SuppressLint("ResourceType")
        Button button = findViewById(R.id.cart_button);
        button.setText(String.format(Locale.getDefault(), "CART (%d)", cart.size()));
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        adapter.setOnItemClickListener(this);
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
        headers.add(new Header(client.getClientName()));
        return headers;
    }

    @Override
    List<Footer> footerList() {
        List<Footer> footers = new ArrayList<>();
        Footer footer = new Footer(this,
                "Cart (0)",
                R.id.cart_button,
                android.R.color.white,
                android.R.color.holo_blue_light,
                this::goToViewCartActivity);
        footers.add(footer);
        return footers;
    }

    @Override
    BaseListAdapter buildAdapter() {
        adapter = new ProductsAdapter(products);
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
            products.clear();
            products.addAll(result.data);
            adapter.notifyDataSetChanged();
        }
        setRefreshing(false);
    }

    @Override
    public void onItemClick(Product data) {
        CartDialog dialog = new CartDialog(this, this);
        dialog.show();
        selectedProduct = data;
    }

    @Override
    public void onProceedAdd(int quantity) {
        if (quantity > 0) {
            addToCart(new Order(selectedProduct, quantity));
        }
    }
}
