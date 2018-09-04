package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.controls.dialogs.DeletePaymentDialog;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.PaymentGroup;
import com.nkraft.eyebox.models.dao.PaymentsDao;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.PaymentService;
import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentsActivity extends ListActivity<Payment> implements
        BaseListAdapter.ItemClickListener<Payment>,
        BaseListAdapter.LongItemClickListener<Payment>,
        TaskWrapper.Task<PagedResult<List<Payment>>> {

    private TaskWrapper<PagedResult<List<Payment>>> paymentTask = new TaskWrapper<>(this);

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.payment_transactions);
        async(() -> {
            List<PaymentGroup> paymentList = database().payments().getGroupedPayments();
            List<Payment> payments = new ArrayList<>();
            for(PaymentGroup paymentGroup : paymentList) {
                Payment payment = toPayment(paymentGroup);
                payments.add(payment);
            }
            setDataList(payments);
            runOnUiThread(this::notifyDataSetChanged);
        });
        paymentTask.execute();
    }

    @Override
    List<Header> headerList() {
        return new ArrayList<Header>() {{
            add(new Header(R.string.client));
            add(new Header(R.string.total_payment));
        }};
    }

    @Override
    BaseListAdapter initializeAdapter() {
        PaymentsAdapter adapter = new PaymentsAdapter(getDataList());
        adapter.setOnItemClickListener(this);
        adapter.setOnLongClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(Payment data) {
        Intent intent = new Intent(this, PaymentDetailsActivity.class);
        intent.putExtra("payment", data);
        startActivity(intent);
    }

    @Override
    public boolean onLongItemClick(Payment data) {
        DeletePaymentDialog dialog = new DeletePaymentDialog(this, data);
        dialog.setDeleteListener(this::deleteItem);
        dialog.show();
        return true;
    }

    @Override
    String[] getSearchableFields(Payment payment) {
        return new String[] { payment.getCheckName() };
    }

    void deleteItem(Payment payment) {
        if (!payment.isSafeToDelete()) {
            showAlertDialog("Error", "Action not allowed");
            return;
        }
        int position = getIndexOf(payment);
        async(() -> {
            int rows = database().payments().deleteByProductNumber(payment.getProductNumber());
            if (rows > 0) {
                removeDataAt(position);
                runOnUiThread(() -> notifyItemRemoved(position));
            }
            else {
                runOnUiThread(() ->
                    showSnackbar(
                        "There's a problem deleting entry",
                        "Retry", (v) -> deleteItem(payment)
                    )
                );
            }
        });
    }

    int counter;
    Payment toPayment(PaymentGroup paymentGroup) {
        Payment payment = new Payment();
        payment.setId(new Date().getTime() + (++counter));
        payment.setCustomerId(paymentGroup.getCustomerId());
        payment.setCustomerName(paymentGroup.getClientName());
        payment.setProductNumber(paymentGroup.getProductNumber());
        payment.setCheckDate(paymentGroup.getPayDate());
        payment.setStatus(paymentGroup.getStatus());
        payment.setAmount(paymentGroup.getTotalPayment());
        return  payment;
    }

    @Override
    public void onTaskBegin() {
        showLoader(true, "Checking status...");
    }

    @Override
    public PagedResult<List<Payment>> onTaskExecute() {
        PaymentService paymentService = PaymentService.instance();
        List<Payment> submittedPayments = database().payments().getAllSubmittedPayments();
        if (submittedPayments.isEmpty()) {
            return new PagedResult<>(new ArrayList<>(), 0);
        }
        return paymentService.syncPaymentsStatus(submittedPayments);
    }

    @Override
    public void onTaskEnd(PagedResult<List<Payment>> result) {
        showLoader(false);
        if (result.isSuccess()) {
            onPaymentsSync(result.data);
        }
        else {
            onPaymentsNotSync(result.errorMessage);
        }
    }

    private void onPaymentsSync(List<Payment> payments) {
        async(() -> {
            List<Payment> submittedPayments = database().payments().getAllSubmittedPayments();
            for (Payment updatedPayment : payments) {
                for (Payment oldPayment : submittedPayments) {
                    if (updatedPayment.equals(oldPayment)) {
                        String status = updatedPayment.getStatus();
                        if (status != null && !status.equals("unsubmitted")) {
                            oldPayment.setStatus(updatedPayment.getStatus());
                        }
                        break;
                    }
                }
            }
            database().payments().insertPayments(submittedPayments);
        });
    }

    private void onPaymentsNotSync(String message) {
        Debug.log("sync payments response: %s", message);
        showSnackbar("An error occurred.");
    }
}
