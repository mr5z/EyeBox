package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.PaymentDetailsAdapter;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.PaymentService;
import com.nkraft.eyebox.utils.Formatter;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class PaymentDetailsActivity extends BaseActivity {

    private PaymentDetailsAdapter adapter;
    private List<Payment> payments = new ArrayList<>();

    @BindView(R.id.payment_details_list)
    RecyclerView paymentDetailList;

    @BindView(R.id.ap_chk_check_all)
    CheckBox chkCheckAll;

    @BindView(R.id.ap_txt_client_name)
    TextView txtClientName;

    @BindView(R.id.ap_txt_amount)
    TextView txtAmount;

    @OnClick(R.id.ap_btn_remove)
    void onRemoveClick(View view) {
        for(Iterator<Payment> iterator = payments.iterator(); iterator.hasNext(); ) {
            Payment payment = iterator.next();
            if (payment.isChecked()) {
                removePaymentAsync(payment, iterator);
            }
        }
    }

    void removePaymentAsync(Payment payment, Iterator<Payment> iterator) {
        if (!payment.isSafeToDelete()) {
            showAlertDialog("Not allowed", "Unable to delete");
            return;
        }
        async(() -> {
            int affectedRows = database().payments().deletePayment(payment.getId());
            if (affectedRows > 0) {
                runOnUiThread(() -> {
                    iterator.remove();
                    int position = payments.indexOf(payment);
                    adapter.notifyItemRemoved(position);
                });
            }
        });
    }

    @OnCheckedChanged(R.id.ap_chk_check_all)
    void onCheckAll(CompoundButton compoundButton, boolean checked) {
        if (adapter == null)
            return;

        for (Payment payment : payments) {
            payment.setChecked(checked);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_show_print_ativity)
    void onPrintSelectedClick(View view) {
        ArrayList<Payment> checkedPayments = getCheckedPayments();
        if (checkedPayments.isEmpty())
            return;

        Intent intent = new Intent(this, PrintTemplateActivity.class);
        intent.putParcelableArrayListExtra("payments", checkedPayments);
        startActivity(intent);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.payment_breakdown);
        Payment payment = getPayment();
        txtClientName.setText(Formatter.string("Client: %s", payment.getCustomerName()));
        txtAmount.setText(Formatter.string("Payment Amount: %s", payment.getFormattedAmount()));
        async(() -> {
            String productNumber = payment.getProductNumber();
            List<Payment> dataList = database().payments().getPaymentsByProductNumber(productNumber);
            runOnUiThread(() -> {
                payments.addAll(dataList);
                adapter = new PaymentDetailsAdapter(payments);
                paymentDetailList.setLayoutManager(new LinearLayoutManager(this));
                paymentDetailList.setAdapter(adapter);
            });
        });
    }

    @Override
    int contentLayout() {
        return R.layout.activity_payment_details;
    }

    private Payment getPayment() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("payment");
    }

    private ArrayList<Payment> getCheckedPayments() {
        ArrayList<Payment> checkedPayments = new ArrayList<>();
        for(Payment payment : payments) {
            if (payment.isChecked())
                checkedPayments.add(payment);
        }
        return checkedPayments;
    }

    private void updateStatus(Payment paymentStatus, List<Payment> oldPayments) {
        for(Payment p : oldPayments) {
            if (paymentStatus.equals(p)) {
                paymentStatus.setStatus(p.getStatus());
                break;
            }
        }
    }

    private void updateExtraDetails(List<Payment> payments, Payment payment) {
        List<Bank> bankList = database().banks().getBanksByClientId(payment.getCustomerId());
        List<Terms> termsList = database().terms().getAllTerms();
        for(Payment p : payments) {
            for(Bank bank : bankList) {
                if (bank.getId() == p.getBankName()) {
                    p.setBankNameStr(bank.getNamex());
                    break;
                }
            }
            for(Terms terms : termsList) {
                if (terms.getId() == p.getTerms()) {
                    p.setTermsName(terms.getNamex());
                    break;
                }
            }
            p.setReceivedBy(payment.getReceivedBy());
        }
    }
}
