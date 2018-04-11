package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ProductsAdapter;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends ListActivity implements TaskWrapper.Task<PagedResult<List<Product>>> {

    ProductsAdapter adapter;
    List<Product> products = new ArrayList<>();

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        productTask().execute();
    }

    private TaskWrapper<PagedResult<List<Product>>> productTask() {
        return new TaskWrapper<>(this);
    }

    Client getSelectedClient() {
        Intent intent = getIntent();
        return (Client) intent.getSerializableExtra("selectedClient");
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        Client client = getSelectedClient();
        headers.add(new Header(client.getClientName()));
        return headers;
    }

    @Override
    BaseListAdapter buildAdapter() {
        adapter = new ProductsAdapter(products);
        return adapter;
    }

    @Override
    public void onTaskBegin() {
        showLoader(true, R.string.loading_data);
    }

    @Override
    public PagedResult<List<Product>> onTaskExecute() {
        List<Product> productList = database().products().getAllProducts();
        return new PagedResult<>(productList, productList.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<Product>> result) {
        showLoader(false);
        if (result.isSuccess()) {
            products.clear();
            products.addAll(result.data);
            adapter.notifyDataSetChanged();
        }
    }
}
