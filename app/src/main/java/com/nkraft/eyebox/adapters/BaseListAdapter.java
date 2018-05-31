package com.nkraft.eyebox.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.IModel;
import com.nkraft.eyebox.utils.Formatter;

import java.util.List;
import java.util.Locale;

public abstract class BaseListAdapter<
        TViewHolder extends RecyclerView.ViewHolder,
        TModel extends IModel>
        extends RecyclerView.Adapter<TViewHolder> {

    public interface ItemClickListener<T> {
        void onItemClick(T data);
    }

    public interface LongItemClickListener<T> {
        boolean onLongItemClick(T data);
    }

    private List<TModel> dataList;
    private int rowLayoutId;

    private ItemClickListener<TModel> itemClickListener;
    private LongItemClickListener<TModel> longItemClickListener;

    public BaseListAdapter(List<TModel> dataList, int rowLayoutId) {
        this.dataList = dataList;
        this.rowLayoutId = rowLayoutId;
        // for animations and optimization
        setHasStableIds(true);
    }

    @Override
    public final void onBindViewHolder(@NonNull TViewHolder holder, int position) {
        TModel data = dataList.get(position);
        onDataBind(holder, data);
        holder.itemView.setLongClickable(true);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(data));
        }
        if (longItemClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> longItemClickListener.onLongItemClick(data));
        }
    }

    @NonNull
    @Override
    public final TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(rowLayoutId, parent, false);
        return onCreateRow(rowView);
    }

    @Override
    public final long getItemId(int position) {
        return dataList.get(position).getId();
    }

    @Override
    public final int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(ItemClickListener<TModel> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(LongItemClickListener<TModel> longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    static String formatString(String message, Object... args) {
        return Formatter.string(message, args);
    }

    static String formatDate(long date) {
        return Formatter.date(date);
    }

    static String formatCurrency(double amount) {
        return Formatter.currency(amount);
    }


    abstract void onDataBind(TViewHolder holder, TModel data);

    abstract TViewHolder onCreateRow(View rowView);

}
