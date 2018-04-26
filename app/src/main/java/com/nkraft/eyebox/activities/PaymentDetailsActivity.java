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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentDetailsActivity extends BaseActivity {

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
        async(() -> {
            Payment payment = getPayment();
            long customerId = payment.getCustomerId();
            List<Payment> dataList = database().payments().getPaymentsByClientId(customerId);
            runOnUiThread(() -> {
                PaymentDetailsAdapter adapter = new PaymentDetailsAdapter(dataList);
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
}
