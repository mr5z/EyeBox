package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.controls.dialogs.DeletePaymentDialog;
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaymentsActivity extends ListActivity<Payment> implements
        BaseListAdapter.ItemClickListener<Payment>,BaseListAdapter.LongItemClickListener<Payment> {

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.payment_transactions);
        async(() -> {
            List<Payment> paymentList = database().payments().getAllPayments();
            setDataList(paymentList);
            runOnUiThread(this::notifyDataSetChanged);
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
    String getSearchableFields(Payment payment) {
        return payment.getCheckName();
    }

    void deleteItem(Payment payment) {
        if (!payment.isSafeToDelete()) {
            showAlertDialog("Get Rekt m8", "");
            return;
        }
        int position = getIndexOf(payment);
        async(() -> {
            int rows = database().payments().deletePayment(payment.getId());
            if (rows > 0) {
                removeDataAt(position);
                runOnUiThread(() -> notifyItemRemoved(position));
            }
            else {
                showSnackbar(
                    "There's a problem deleting entry",
                    "Retry", (v) -> deleteItem(payment)
                );
            }
        });
    }
}
