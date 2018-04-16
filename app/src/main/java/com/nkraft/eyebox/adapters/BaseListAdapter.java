package com.nkraft.eyebox.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nkraft.eyebox.models.Client;

import java.util.List;
import java.util.Locale;

public abstract class BaseListAdapter<TViewHolder extends RecyclerView.ViewHolder, TModel> extends RecyclerView.Adapter<TViewHolder> {

    public interface ItemClickListener<T> {
        void onItemClick(T data);
    }

    private List<TModel> dataList;
    private int rowLayoutId;

    private ItemClickListener<TModel> itemClickListener;

    public BaseListAdapter(List<TModel> dataList, int rowLayoutId) {
        this.dataList = dataList;
        this.rowLayoutId = rowLayoutId;
    }

    @Override
    public final void onBindViewHolder(@NonNull TViewHolder holder, int position) {
        TModel data = dataList.get(position);
        onDataBind(holder, data);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(data));
        }
    }

    @NonNull
    @Override
    public final TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(rowLayoutId, parent, false);
        return onCreateRow(rowView);
    }

    public void setOnItemClickListener(ItemClickListener<TModel> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    String format(String message, Object ...args) {
        return String.format(Locale.getDefault(), message, args);
    }

    @Override
    public final int getItemCount() {
        return dataList.size();
    }

    abstract void onDataBind(TViewHolder holder, TModel data);

    abstract TViewHolder onCreateRow(View rowView);

}
