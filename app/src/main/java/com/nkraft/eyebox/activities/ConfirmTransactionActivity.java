package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.ConfirmTransactionAdapter;
import com.nkraft.eyebox.controls.ConfirmPaymentDialog;
import com.nkraft.eyebox.controls.PaymentAddedDialog;
import com.nkraft.eyebox.controls.TransactionDetailsDialog;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmTransactionActivity extends BaseActivity implements TransactionDetailsDialog.DialogClickListener, ConfirmPaymentDialog.ClickListener, PaymentAddedDialog.ClickListener {

    @BindView(R.id.act_list_client_sales)
    RecyclerView listSales;

    @BindView(R.id.act_txt_client_name)
    TextView txtClientName;

    @BindView(R.id.act_txt_amount)
    TextView txtAmount;

    @BindView(R.id.act_txt_total_balance)
    TextView txtTotalBalance;

    @BindView(R.id.act_txt_total_payment)
    TextView txtTotalPayment;

    private List<Payment> dataList;

    @OnClick(R.id.act_img_edit)
    void onEditClick(View view) {
        Transaction transaction = getTransaction();
        TransactionDetailsDialog dialog = new TransactionDetailsDialog(this, transaction);
        dialog.setDialogClickListener(this);
        dialog.show();
    }

    @OnClick(R.id.act_btn_pay_selected)
    void onPaySelectedClick(View view) {
        showConfirmDialog();
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        initList();
        Transaction transaction = getTransaction();
        txtClientName.setText(String.format(Locale.getDefault(), "Client: %s", transaction.getClientName()));
        txtAmount.setText(String.format(Locale.getDefault(), "The Amount of: %s", transaction.getFormattedBalance()));
        txtTotalBalance.setText(transaction.getFormattedBalance());
        txtTotalPayment.setText("0");
    }

    void showConfirmDialog() {
        ConfirmPaymentDialog dialog = new ConfirmPaymentDialog(this);
        dialog.setClickListener(this);
        dialog.show();
    }

    @Override
    public void onConfirmTransaction() {
        async(() -> {
            Payment payment = dataList.get(0);
            database().payments().insertPayment(payment);
            runOnUiThread(this::showSuccessDialog);
        });
    }

    private void showSuccessDialog() {
        PaymentAddedDialog dialog = new PaymentAddedDialog(this, this);
        dialog.show();
    }

    void initList() {
        dataList = new ArrayList<>();
        for(int i = 0;i < 1; ++i) {
            Transaction transaction = getTransaction();
            Payment payment = new Payment();
            payment.setId(transaction.getId());
            payment.setSalesId(transaction.getId());
            payment.setTerms(transaction.getTerms());
            payment.setOrderNumber(transaction.getOrderNumber());
            payment.setCheckDate(transaction.getCheckDate());
            payment.setClientName(transaction.getClientName());
            payment.setBranch(i);
            payment.setProductNumber(transaction.getProductNumber());
            payment.setTotalPayment(transaction.getBalance());
            dataList.add(payment);
        }
        ConfirmTransactionAdapter adapter = new ConfirmTransactionAdapter(dataList);
        listSales.setLayoutManager(new LinearLayoutManager(this));
        listSales.setAdapter(adapter);
    }

    Transaction getTransaction() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("transaction");
    }

    @Override
    int contentLayout() {
        return R.layout.activity_confirm_transaction;
    }

    @Override
    public void onTransactionUpdated(Transaction transaction) {
        Intent intent = getIntent();
        intent.putExtra("transaction", transaction);
    }

    @Override
    public void onConfirmPayment() {
        popToMainActivity();
    }
}
