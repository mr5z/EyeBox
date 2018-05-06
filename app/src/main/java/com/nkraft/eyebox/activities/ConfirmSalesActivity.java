package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.ConfirmSalesAdapter;
import com.nkraft.eyebox.controls.ConfirmPaymentDialog;
import com.nkraft.eyebox.controls.TransactionDetailsDialog;
import com.nkraft.eyebox.controls.dialogs.AlertDialog;
import com.nkraft.eyebox.controls.dialogs.PaymentAddedDialog;
import com.nkraft.eyebox.models.Credit;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Sale;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmSalesActivity extends BaseActivity {

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


    ConfirmSalesAdapter adapter;
    private List<Sale> dataList = new ArrayList<>();
    private List<Bank> bankList;
    private List<Terms> termsList;

    @OnClick(R.id.act_img_edit)
    void onEditClick(View view) {
        async(() -> {
            bankList = database().banks().getAllBanks();
            termsList = database().terms().getAllTerms();
            runOnUiThread(() -> {
                Transaction transaction = getTransaction();
                TransactionDetailsDialog dialog = new TransactionDetailsDialog(this, transaction, bankList, termsList);
                dialog.setDialogClickListener((t) ->{
                    Intent intent = getIntent();
                    intent.putExtra("transaction", t);
                });
                dialog.show();
            });
        });
    }

    @OnClick(R.id.act_btn_pay_selected)
    void onPaySelectedClick(View view) {
        if (!hasPaymentSelection()) {
            showAlertDialog();
        }
        else {
            showConfirmDialog();
        }
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        initList();
        Transaction transaction = getTransaction();
        txtClientName.setText(String.format(Locale.getDefault(), "Client: %s", transaction.getClientName()));
        txtAmount.setText(Formatter.string("The Amount of: %s", Formatter.currency(transaction.getAmount())));
        txtTotalBalance.setText(Formatter.currency(transaction.getBalance()));
        txtTotalPayment.setText("0");

        async(() -> {
            bankList = database().banks().getAllBanks();
            termsList = database().terms().getAllTerms();
        });
    }

    void showConfirmDialog() {
        ConfirmPaymentDialog dialog = new ConfirmPaymentDialog(this);
        dialog.setClickListener(() -> async(() -> {
            Payment payment = createPayment();
            Credit credit = createCredit(payment.getId());
            database().payments().insertPayment(payment);
            database().credits().insertCredit(credit);
            runOnUiThread(this::showSuccessDialog);
        }));
        dialog.show();
    }

    private void showSuccessDialog() {
        PaymentAddedDialog dialog = new PaymentAddedDialog(this);
        dialog.setOnClickListener((d) -> popToMainActivity());
        dialog.show();
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog(this, "Please select items to pay!");
        alertDialog.show();
    }

    private boolean hasPaymentSelection() {
        for(Sale sale : dataList) {
            if (sale.isChecked())
                return true;
        }
        return false;
    }

    void initList() {
        adapter = new ConfirmSalesAdapter(dataList);
        adapter.setCheckListener(this::updateTotalPayment);
        listSales.setLayoutManager(new LinearLayoutManager(this));
        listSales.setAdapter(adapter);
        async(() -> {
            long customerId = getTransaction().getId();
            List<Sale> sales = database().sales().getActiveSalesByCustomerId(customerId);
            dataList.clear();
            dataList.addAll(sales);
            updateSalesInteractiveness();
            runOnUiThread(adapter::notifyDataSetChanged);
        });
    }

    Transaction getTransaction() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("transaction");
    }

    @Override
    int contentLayout() {
        return R.layout.activity_confirm_transaction;
    }

    private double getTotalPayment() {
        double totalPayment = 0;
        for(Sale sale : dataList) {
            if (sale.isChecked()) {
                totalPayment += sale.getTotalAmount();
            }
        }
        return totalPayment;
    }

    private double getTotalPayable() {
        double totalPayable = 0;
        for(Sale sale : dataList) {
            totalPayable += sale.getTotalAmount();
        }
        return totalPayable;
    }

    private Payment createPayment() {
        User user = AccountService.instance().currentUser;
        Transaction transaction = getTransaction();
        Payment payment = new Payment();
        payment.setId((new Date()).getTime());
        payment.setCustomerId(transaction.getId());
        payment.setStatus("unsubmitted");
        payment.setCheckName(transaction.getClientName());
        payment.setCheckDate((new Date()).getTime());
        payment.setSalesDate((new Date()).getTime());
        payment.setAmount(getTotalPayment());
        payment.setBranchNo(user.getAssignedBranch());
        payment.setTerms(transaction.getTerms());
        payment.setBankName(transaction.getBank());
        payment.setValidatedBy(user.getId());
        payment.setReceivedBy(user.getId());
        payment.setReceiverName(user.getName());
        payment.setSalesId(generateId(2018));
        payment.setPrNo(String.valueOf(generateId(50320)));
        return payment;
    }

    private Credit createCredit(long paymentId) {
        Transaction transaction = getTransaction();
        Credit credit = new Credit();
        credit.setId(paymentId);
        credit.setPayAmount(getTotalPayment());
        credit.setCustomerId(transaction.getId());
        credit.setDateX(new Date().getTime());
        credit.setPayId(new Date().getTime());
        credit.setPrNo(new Date().getTime());
        credit.setSalesId(new Date().getTime());
        credit.setTotalPayable(getTotalPayable());
        return credit;
    }

    static long generateId(long prefix) {
        return Long.parseLong(prefix + "" + (new Date().getTime() / 1000));
    }

    private void updateTotalPayment(boolean checked, Sale data) {
        double totalPayment = 0;
        for (Sale sale : dataList) {
            if (sale.isChecked()) {
                totalPayment += sale.getTotalAmount();
            }
        }
        txtTotalPayment.setText(Formatter.currency(totalPayment));
        updateSalesInteractiveness();
        adapter.notifyDataSetChanged();
    }

    private void updateSalesInteractiveness() {
        Transaction transaction = getTransaction();
        double remainingPayment = transaction.getAmount();
        for(Sale sale : dataList) {
            remainingPayment = remainingPayment - (sale.isChecked() ? sale.getTotalAmount() : 0);
        }
        for(Sale sale : dataList) {
            if (!sale.isChecked()) {
                sale.setDisabled(remainingPayment < sale.getTotalAmount());
            }
        }
    }

}
