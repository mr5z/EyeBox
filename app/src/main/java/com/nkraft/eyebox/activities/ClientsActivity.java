package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ClientsAdapter;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends ListActivity
        implements
        BaseListAdapter.ItemClickListener<Client>,
        TaskWrapper.Task<PagedResult<List<Client>>> {

    private TaskWrapper<PagedResult<List<Client>>> customerTask() {
        return new TaskWrapper<>(this);
    }

    private ClientsAdapter adapter;
    private List<Client> clients = new ArrayList<>();

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
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
            clients.clear();
            clients.addAll(result.data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    BaseListAdapter getAdapter() {
        adapter = new ClientsAdapter(clients);
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

    boolean isMakingSignature() {
        Intent intent = getIntent();
        return intent.getBooleanExtra("isMakingSignature", false);
    }

}
