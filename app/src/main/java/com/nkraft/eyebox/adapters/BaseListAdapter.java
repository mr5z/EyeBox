package com.nkraft.eyebox.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseListAdapter<TViewHolder extends RecyclerView.ViewHolder, TModel> extends RecyclerView.Adapter<TViewHolder> {

    private List<TModel> dataList;
    private int rowLayoutId;

    public BaseListAdapter(List<TModel> dataList, int rowLayoutId) {
        this.dataList = dataList;
        this.rowLayoutId = rowLayoutId;
    }

    @Override
    public final void onBindViewHolder(@NonNull TViewHolder holder, int position) {
        TModel data = dataList.get(position);
        onDataBind(holder, data);
    }

    @NonNull
    @Override
    public final TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(rowLayoutId, parent, false);
        return onCreateRow(rowView);
    }

    @Override
    public final int getItemCount() {
        return dataList.size();
    }

    abstract void onDataBind(TViewHolder holder, TModel data);

    abstract TViewHolder onCreateRow(View rowView);

}
