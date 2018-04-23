package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentsActivity extends ListActivity implements BaseListAdapter.ItemClickListener<Payment> {

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
    }

    @Override
    List<Header> headerList() {
        return new ArrayList<Header>() {{
            add(new Header(R.string.client));
            add(new Header(R.string.total_payment));
        }};
    }

    @Override
    BaseListAdapter buildAdapter() {
        adapter = new PaymentsAdapter(payments);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(Payment data) {
        Intent intent = new Intent(this, PaymentDetailsActivity.class);
        intent.putExtra("payment", data);
        startActivity(intent);
    }
}
