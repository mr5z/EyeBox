package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;

import java.util.List;

public class PaymentsAdapter extends BaseListAdapter<PaymentsAdapter.ViewHolder, Payment> {

    public PaymentsAdapter(List<Payment> dataList) {
        super(dataList, R.layout.payment_row);
    }

    @Override
    void onDataBind(ViewHolder holder, Payment data) {
        holder.txtClientName.setText(data.getCheckName());
        holder.txtProductNumber.setText(data.getPrNo());
        holder.txtTransactionDate.setText(formatDate(data.getCheckDate()));
        holder.txtTotalPayment.setText(data.getFormattedAmount());
        holder.txtTransactionStatus.setText(convertStatusToSomething(data.getStatus()));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    private static String convertStatusToSomething(String status) {
        switch (status) {
            case "unsubmitted":
                return "PENDING";
            case "pending":
                return "";
            default:
                return "";
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtClientName;
        TextView txtProductNumber;
        TextView txtTransactionDate;
        TextView txtTotalPayment;
        TextView txtTransactionStatus;

        ViewHolder(View itemView) {
            super(itemView);
            txtClientName = itemView.findViewById(R.id.txt_client_name);
            txtProductNumber = itemView.findViewById(R.id.txt_product_number);
            txtTransactionDate = itemView.findViewById(R.id.txt_transaction_date);
            txtTotalPayment = itemView.findViewById(R.id.txt_total_payment);
            txtTransactionStatus = itemView.findViewById(R.id.txt_transaction_status);
        }
    }
}
