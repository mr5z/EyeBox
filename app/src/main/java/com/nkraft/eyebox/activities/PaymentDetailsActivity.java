package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentDetailsAdapter;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.PaymentService;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentDetailsActivity extends BaseActivity implements TaskWrapper.Task<PagedResult<List<Payment>>> {

    private TaskWrapper<PagedResult<List<Payment>>> paymentsTask() {
        return new TaskWrapper<>(this);
    }

    @BindView(R.id.payment_details_list)
    RecyclerView paymentDetailList;

    @OnClick(R.id.btn_show_print_ativity)
    void onPrintSelectedClick(View view) {
        Intent intent = new Intent(this, PrintTemplateActivity.class);
        intent.putExtra("payment", getPayment());
        startActivity(intent);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        paymentsTask().execute();
    }

    @Override
    int contentLayout() {
        return R.layout.activity_payment_details;
    }

    private Payment getPayment() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("payment");
    }

    @Override
    public void onTaskBegin() {
        showLoader(true, "Checking status...");
    }

    @Override
    public PagedResult<List<Payment>> onTaskExecute() {
        PaymentService paymentService = PaymentService.instance();
        Payment payment = getPayment();
        long customerId = payment.getCustomerId();
        List<Payment> dataList = database().payments().getPaymentsByClientId(customerId);
        List<Bank> bankList = database().banks().getAllBanks();
        List<Terms> termsList = database().terms().getAllTerms();
        PagedResult<List<Payment>> statusResult = paymentService.checkPaymentsStatus(customerId);
        if (statusResult.isSuccess()) {
            for(Payment p : dataList) {
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
                p.setBranchNo(payment.getBranchNo());
            }
            for(Payment p : statusResult.data) {
                updateStatus(p, dataList);
            }
        }
        async(() -> database().payments().insertPayments(dataList));
        return new PagedResult<>(dataList, dataList.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<Payment>> result) {
        showLoader(false);
        if (!result.isSuccess())
            return;
        PaymentDetailsAdapter adapter = new PaymentDetailsAdapter(result.data);
        paymentDetailList.setLayoutManager(new LinearLayoutManager(this));
        paymentDetailList.setAdapter(adapter);
    }

    void updateStatus(Payment paymentStatus, List<Payment> oldPayments) {
        for(Payment p : oldPayments) {
            if (paymentStatus.equals(p)) {
                paymentStatus.setStatus(p.getStatus());
                break;
            }
        }
    }
}
