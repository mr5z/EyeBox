package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Payment;

import java.util.List;

public class ClientsAdapter extends BaseListAdapter<ClientsAdapter.ViewHolder, Client> {

    public interface ItemClickListener {
        void onItemClick(Client data);
    }

    private ItemClickListener itemClickListener;

    public ClientsAdapter(List<Client> dataList) {
        super(dataList, R.layout.client_row);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    void onDataBind(ClientsAdapter.ViewHolder holder, Client data) {
        holder.txtClientName.setText(data.getClientName());
        holder.txtClientAddress.setText(data.getClientAddress());
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(data));
        }
    }

    @Override
    ClientsAdapter.ViewHolder onCreateRow(View rowView) {
        return new ClientsAdapter.ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtClientName;
        TextView txtClientAddress;

        ViewHolder(View itemView) {
            super(itemView);
            txtClientName = itemView.findViewById(R.id.client_name);
            txtClientAddress = itemView.findViewById(R.id.client_address);
        }
    }
}
