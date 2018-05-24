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
import java.util.Calendar;
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

    private ConfirmSalesAdapter adapter;
    private Transaction transaction;
    private List<Sale> dataList = new ArrayList<>();

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        transaction = getTransaction();
        txtClientName.setText(String.format(Locale.getDefault(), "Client: %s", transaction.getClientName()));
        txtAmount.setText(Formatter.string("The Amount of: %s", Formatter.currency(transaction.getAmount())));
        txtTotalBalance.setText(Formatter.currency(transaction.getBalance()));
        txtTotalPayment.setText("0");
        initList();
    }

    @OnClick(R.id.act_img_edit)
    void onEditClick(View view) {
        async(() -> {
            List<Bank> bankList = database().banks().getBanksByClientId(transaction.getId());
            List<Terms> termsList = database().terms().getAllTerms();
            runOnUiThread(() -> {
                TransactionDetailsDialog dialog =
                        new TransactionDetailsDialog(this, transaction, bankList, termsList);
                dialog.setDialogClickListener(this::updateCurrentTransaction);
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

    void updateCurrentTransaction(Transaction transaction) {
        this.transaction = transaction;
        updateSalesInteractiveness();
        adapter.notifyDataSetChanged();
        txtAmount.setText(Formatter.string("The Amount of: %s", Formatter.currency(transaction.getAmount())));
    }

    void showConfirmDialog() {
        ConfirmPaymentDialog dialog = new ConfirmPaymentDialog(this);
        dialog.setClickListener((includeExcess) -> async(() -> {
            List<Payment> payment = createPayments();
//            double excessCredits = transaction.getAmount() - getTotalPayment();
////            if (includeExcess && excessCredits > 0) {
////                database().credits().insertCredit(createCredit());
////            }
            database().payments().insertPayments(payment);
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
            long customerId = transaction.getId();
            List<Sale> sales = database().sales().getActiveSalesByCustomerId(customerId);
            dataList.clear();
            dataList.addAll(sales);
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

    private List<Payment> createPayments() {
        List<Payment> checkedPayments = new ArrayList<>();
        User currentUser = AccountService.instance().currentUser;
        int idPrefixer = -1;
        for(Sale sale : dataList) {
            if (!sale.isChecked())
                continue;

            long id = generateId(++idPrefixer);
            Payment payment = new Payment();
            payment.setId(id);
            payment.setSalesId(generateId((long) (Math.random() * 1000)));
            payment.setReceivedBy(currentUser.getId());
            payment.setReceiverName(currentUser.getName());
            payment.setBranchNo(currentUser.getAssignedBranch());
            payment.setReceivedBy(currentUser.getId());
            payment.setPrNo(transaction.getProductNumber());
            payment.setOrNo(transaction.getOrderNumber());
            payment.setCheckNo(transaction.getCheckNumber());
            payment.setCustomerId(transaction.getId());
            payment.setTerms(transaction.getTerms());
            payment.setBankName(transaction.getBank());
            payment.setCustomerName(transaction.getClientName());
            payment.setCheckDate(transaction.getCheckDate());
            payment.setAmount(sale.getAmount());
            payment.setStatus("unsubmitted");
            checkedPayments.add(payment);
        }
        return checkedPayments;
    }

    private Credit createCredit() {
        double excessCredits = transaction.getAmount() - getTotalPayment();
        Credit credit = new Credit();
        credit.setId(new Date().getTime());
        credit.setExcess(excessCredits);
        credit.setPayAmount(getTotalPayment());
        credit.setCustomerId(transaction.getId());
        credit.setDateX(new Date().getTime());
        credit.setPayId(new Date().getTime());
//        credit.setPrNo(transaction.getProductNumber());
        credit.setSalesId(new Date().getTime());
        credit.setTotalPayable(getTotalPayable());
        return credit;
    }

    static long generateId(long prefix) {
        return Long.parseLong(prefix + "" + (new Date().getTime() / 1000));
    }

    private void updateTotalPayment(boolean checked, Sale data) {
        double totalPayment = 0;
        double remainingAmount = transaction.getAmount();
        for (Sale sale : dataList) {
            if (sale.isChecked()) {
                double payment = sale.getTotalAmount();
                double difference = remainingAmount - payment;
                double subTotalPayment = Math.min(payment, remainingAmount);
                remainingAmount = Math.max(0, difference);
                totalPayment += subTotalPayment;
                sale.setAmount(subTotalPayment);
            }
        }
        txtTotalPayment.setText(Formatter.currency(totalPayment));
        updateSalesInteractiveness();
        adapter.notifyDataSetChanged();
    }

    private void updateSalesInteractiveness() {
        if (!hasPaymentSelection()) {
            for(Sale sale : dataList) {
                sale.setDisabled(false);
            }
            return;
        }
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
