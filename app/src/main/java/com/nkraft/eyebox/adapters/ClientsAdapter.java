package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Client;

import java.util.List;

public class ClientsAdapter extends BaseListAdapter<ClientsAdapter.ViewHolder, Client> {

    public ClientsAdapter(List<Client> dataList) {
        super(dataList, R.layout.client_row);
    }

    @Override
    void onDataBind(ViewHolder holder, Client data) {
        holder.txtClientName.setText(data.getName());
        holder.txtClientAddress.setText(data.getAddress());
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
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
