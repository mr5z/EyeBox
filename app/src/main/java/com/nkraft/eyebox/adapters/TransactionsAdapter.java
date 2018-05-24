package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Transaction;

import java.util.List;

public class TransactionsAdapter extends BaseListAdapter<TransactionsAdapter.ViewHolder, Transaction> {

    public TransactionsAdapter(List<Transaction> dataList) {
        super(dataList, R.layout.row_transaction);
    }

    @Override
    void onDataBind(ViewHolder holder, Transaction data) {
        holder.txtTitle.setText(data.getClientName());
        holder.txtDescription.setText(data.getClientAddress());
        holder.txtBalance.setText(formatCurrency(data.getBalance()));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView txtTitle;
        final TextView txtDescription;
        final TextView txtBalance;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtBalance = itemView.findViewById(R.id.txt_balance);
        }
    }
}
