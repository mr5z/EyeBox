package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.controls.dialogs.DeletePaymentDialog;
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentsActivity extends ListActivity implements
        BaseListAdapter.ItemClickListener<Payment>,BaseListAdapter.LongItemClickListener<Payment> {

    private List<Payment> payments = new ArrayList<>();
    private PaymentsAdapter adapter;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        async(() -> {
            List<Payment> paymentList = database().payments().getAllPayments();
            payments.clear();
            payments.addAll(paymentList);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
        setContextualMenuListener((position -> {
            payments.remove(position);
            adapter.notifyItemRemoved(position);
            return true;
        }));
    }

    @Override
    List<Header> headerList() {
        return new ArrayList<Header>() {{
            add(new Header(R.string.client));
            add(new Header(R.string.total_payment));
        }};
    }

    @Override
    BaseListAdapter getAdapter() {
        adapter = new PaymentsAdapter(payments);
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

    void deleteItem(Payment payment) {
        int position = payments.indexOf(payment);
        async(() -> {
            int rows = database().payments().deletePayment(payment.getId());
            runOnUiThread(() -> {
                if (rows > 0) {
                    payments.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            });
        });
    }
}
