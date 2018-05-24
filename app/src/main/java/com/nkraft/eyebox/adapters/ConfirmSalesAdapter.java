package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Sale;
import com.nkraft.eyebox.utils.Formatter;

import java.util.List;

public class ConfirmSalesAdapter extends BaseListAdapter<ConfirmSalesAdapter.ViewHolder, Sale> implements CompoundButton.OnCheckedChangeListener {


    public interface CheckListener {
        void onCheck(boolean checked, Sale data);
    }

    private CheckListener checkListener;

    public ConfirmSalesAdapter(List<Sale> dataList) {
        super(dataList, R.layout.row_confirm_sales);
    }

    @Override
    void onDataBind(ViewHolder holder, Sale data) {
        holder.chkSales.setTag(data);
        holder.chkSales.setOnCheckedChangeListener(null);
        holder.chkSales.setChecked(data.isChecked());
        holder.chkSales.setOnCheckedChangeListener(this);
        holder.chkSales.setEnabled(!data.isDisabled());
        holder.chkSales.setText(data.getFormattedId());
        holder.txtSalesDate.setText(formatDate(data.getSalesDate()));
        holder.txtDueDate.setText(formatDate(data.getDueDate()));
        holder.txtSoNumber.setText(data.getFormattedTransaction());
        holder.txtTotalNet.setText(Formatter.currency(data.getPayAmount()));
        holder.txtBalance.setText(Formatter.currency(data.getTotalAmount()));
        double payment = data.isChecked() ? data.getAmount() : 0;
        holder.txtPayment.setText(Formatter.currency(payment));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        Sale sale = (Sale) compoundButton.getTag();
        sale.setChecked(checked);
        if (checkListener != null) {
            checkListener.onCheck(checked, sale);
        }
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
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
