package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;

import java.util.List;

public class ConfirmTransactionAdapter extends BaseListAdapter<ConfirmTransactionAdapter.ViewHolder, Payment> {

    public ConfirmTransactionAdapter(List<Payment> dataList) {
        super(dataList, R.layout.row_confirm_transaction);
    }

    @Override
    void onDataBind(ViewHolder holder, Payment data) {
        holder.chkSales.setText(data.getFormattedSalesId());
        holder.txtSalesDate.setText(data.getFormattedCheckDate());
        holder.txtDueDate.setText(data.getFormattedPayDate());
        holder.txtSoNumber.setText(data.getOrderNumber());
        holder.txtTotalNet.setText(data.getFormattedTotalPayment());
        holder.txtBalance.setText(data.getFormattedTotalPayment());
        holder.txtPayment.setText(data.getFormattedTotalPayment());
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkSales;
        TextView txtSalesDate;
        TextView txtDueDate;
        TextView txtSoNumber;
        TextView txtTotalNet;
        TextView txtPreviousPayment;
        TextView txtBalance;
        TextView txtPayment;

        ViewHolder(View itemView) {
            super(itemView);
            chkSales = itemView.findViewById(R.id.ct_row_chk_sales);
            txtSalesDate = itemView.findViewById(R.id.ct_row_txt_sales_date);
            txtDueDate = itemView.findViewById(R.id.ct_row_txt_due_date);
            txtSoNumber = itemView.findViewById(R.id.ct_row_txt_so_number);
            txtTotalNet = itemView.findViewById(R.id.ct_row_txt_total_net);
            txtPreviousPayment = itemView.findViewById(R.id.ct_row_txt_previous_payment);
            txtBalance = itemView.findViewById(R.id.ct_row_txt_balance);
            txtPayment = itemView.findViewById(R.id.ct_row_txt_payment);
        }
    }
}
