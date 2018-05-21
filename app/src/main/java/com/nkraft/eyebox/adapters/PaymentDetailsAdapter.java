package com.nkraft.eyebox.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.utils.Formatter;

import java.util.List;

public class PaymentDetailsAdapter extends BaseListAdapter<PaymentDetailsAdapter.ViewHolder, Payment> implements CompoundButton.OnCheckedChangeListener {

    public PaymentDetailsAdapter(List<Payment> dataList) {
        super(dataList, R.layout.payment_details_row);
    }

    @Override
    void onDataBind(ViewHolder holder, Payment data) {
        holder.chkPaymentId.setTag(data);
        holder.chkPaymentId.setOnCheckedChangeListener(null);
        holder.chkPaymentId.setChecked(data.isChecked());
        holder.chkPaymentId.setOnCheckedChangeListener(this);
        holder.chkPaymentId.setText(Formatter.string("ID: %d", data.getId()));
        holder.txtPayDate.setText(formatDate(data.getDueDate()));
        holder.txtBankName.setText(data.getBankNameStr());
        holder.txtCheckDate.setText(formatDate(data.getCheckDate()));
        holder.txtCheckNo.setText(data.getCheckNo());
        holder.txtAmount.setText(data.getFormattedAmount());
        holder.txtReceivedBy.setText(data.getReceiverName());
        holder.txtSalesId.setText(String.valueOf(data.getSalesId()));
        holder.txtTerms.setText(data.getTermsName());
        holder.txtBranch.setText(String.valueOf(data.getBranchNo()));
        holder.txtProductNumber.setText(data.getPrNo());
        holder.txtOrderNumber.setText(data.getOrNo());
        holder.statusView.setBackgroundColor(toStatusColor(data.getStatus()));
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    private int toStatusColor(String status) {
        if (status != null) {
            return status.equals("OK") ? Color.GREEN : Color.RED;
        }
        return Color.RED;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        Payment payment = (Payment) compoundButton.getTag();
        payment.setChecked(checked);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox chkPaymentId;
        TextView txtPayDate;
        TextView txtBankName;
        TextView txtCheckDate;
        TextView txtCheckNo;
        TextView txtAmount;
        TextView txtReceivedBy;
        TextView txtSalesId;
        TextView txtTerms;
        TextView txtBranch;
        TextView txtProductNumber;
        TextView txtOrderNumber;
        View statusView;

        ViewHolder(View itemView) {
            super(itemView);

            chkPaymentId = itemView.findViewById(R.id.pd_chk_payment_id);
            txtPayDate = itemView.findViewById(R.id.pd_txt_pay_date);
            txtBankName = itemView.findViewById(R.id.pd_txt_bank_name);
            txtCheckDate = itemView.findViewById(R.id.pd_txt_check_date);
            txtCheckNo = itemView.findViewById(R.id.pd_txt_check_no);
            txtAmount = itemView.findViewById(R.id.pd_txt_amount);
            txtReceivedBy = itemView.findViewById(R.id.pd_txt_received_by);
            txtSalesId = itemView.findViewById(R.id.pd_txt_sales_id);
            txtTerms = itemView.findViewById(R.id.pd_txt_terms);
            txtBranch = itemView.findViewById(R.id.pd_txt_branch);
            txtProductNumber = itemView.findViewById(R.id.pd_txt_pr_number);
            txtOrderNumber = itemView.findViewById(R.id.pd_txt_or_number);
            statusView = itemView.findViewById(R.id.pd_status);
        }
    }
}
