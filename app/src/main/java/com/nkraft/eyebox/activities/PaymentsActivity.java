package com.nkraft.eyebox.activities;

import android.content.Intent;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PaymentsActivity extends ListActivity implements BaseListAdapter.ItemClickListener<Payment> {

    @Override
    List<Header> headerList() {
        return new ArrayList<Header>() {{
            add(new Header(R.string.client));
            add(new Header(R.string.total_payment));
        }};
    }

    @Override
    BaseListAdapter buildAdapter() {
        List<Payment> payments = new ArrayList<>();
        for(int i = 0;i < 5; ++i) {
            Payment payment = new Payment();
            payment.setClientName("Client #" + i);
            payment.setProductNumber(UUID.randomUUID().toString().substring(0, 10));
            payment.setStatus(i % 2 == 0 ? "PENDING" : "CANCELLED");
            payment.setTotalPayment((float) (Math.random() * 500));
            payment.setTransactionDate((new Date()).getTime());
            payments.add(payment);
        }
        PaymentsAdapter adapter = new PaymentsAdapter(payments);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(Payment data) {
        startActivity(new Intent(this, PaymentDetailsActivity.class));
    }
}
