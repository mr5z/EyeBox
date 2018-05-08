package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ClientsAdapter;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class ClientsActivity extends ListActivity<Client>
        implements
        BaseListAdapter.ItemClickListener<Client>,
        TaskWrapper.Task<PagedResult<List<Client>>> {

    private TaskWrapper<PagedResult<List<Client>>> customerTask() {
        return new TaskWrapper<>(this);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.select_client);
        customerTask().execute();
    }

    @Override
    public void onTaskBegin() {
        showLoader(true, R.string.loading_data);
    }

    @Override
    public PagedResult<List<Client>> onTaskExecute() {
        List<Client> clients = database().clients().getAllClients();
        return new PagedResult<>(clients, clients.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<Client>> result) {
        showLoader(false);
        if (result.isSuccess()) {
            setDataList(result.data);
            notifyDataSetChanged();
        }
    }

    @Override
    BaseListAdapter initializeAdapter() {
        ClientsAdapter adapter = new ClientsAdapter(getDataList());
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(Client data) {
        Intent intent = new Intent();
        if (isMakingSignature()) {
            intent.setClass(this, SignatureActivity.class);
        }
        else {
            intent.setClass(this, ProductsActivity.class);
        }
        intent.putExtra("selectedClient", data);
        startActivity(intent);
    }

    @Override
    String getSearchableFields(Client client) {
        return client.getName();
    }

    boolean isMakingSignature() {
        Intent intent = getIntent();
        return intent.getBooleanExtra("isMakingSignature", false);
    }
}
