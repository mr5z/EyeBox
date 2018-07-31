package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.VisitsAdapter;
import com.nkraft.eyebox.controls.dialogs.DeleteVisitDialog;
import com.nkraft.eyebox.models.Visit;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VisitsActivity extends BaseActivity implements TaskWrapper.Task<List<Visit>>,BaseListAdapter.LongItemClickListener<Visit> {

    private List<Visit> dataList = new ArrayList<>();
    private VisitsAdapter adapter;

    @BindView(R.id.visits_list_view)
    RecyclerView visitList;

    private TaskWrapper<List<Visit>> visitTask() {
        return new TaskWrapper<>(this);
    }

    void configureList() {
        visitList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VisitsAdapter(dataList);
        adapter.setOnLongClickListener(this);
        visitList.setAdapter(adapter);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        configureList();
        visitTask().execute();
    }

    @OnClick(R.id.fab_select_client)
    void onFabClick(View view) {
        Intent intent = new Intent(this, ClientsActivity.class);
        intent.putExtra("isMakingSignature", true);
        startActivity(intent);
    }

    @Override
    public void onTaskBegin() {
        showLoader(true);
    }

    @Override
    public List<Visit> onTaskExecute() {
        return database().visits().getAllVisits();
    }

    @Override
    public void onTaskEnd(List<Visit> visits) {
        showLoader(false);
        dataList.clear();
        dataList.addAll(visits);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onLongItemClick(Visit data) {
        DeleteVisitDialog dialog = new DeleteVisitDialog(this, data);
        dialog.setDeleteListener(this::deleteItem);
        dialog.show();
        return true;
    }

    boolean deleteItem(Visit data) {
        async(() -> {
            int rowAffected = database().visits().deleteById(data.getId());
            runOnUiThread(() -> {
                if (rowAffected > 0) {
                    dataList.remove(data);
                    adapter.notifyDataSetChanged();
                }
                else {
                    showSnackbar("An error occurred", "Retry", (v) -> deleteItem(data));
                }
            });

        });
        return true;
    }

    @Override
    int contentLayout() {
        return R.layout.activity_visits;
    }
}
