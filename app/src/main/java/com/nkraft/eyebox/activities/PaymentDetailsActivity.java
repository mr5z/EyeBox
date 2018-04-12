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
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    BaseListAdapter getAdapter() {
        List<Payment> dataList = new ArrayList<>();
        for (int i = 0;i < 1; ++i) {
            Payment payment = getPayment();
            dataList.add(payment);
        }
        return new PaymentDetailsAdapter(dataList);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        paymentDetailList.setLayoutManager(new LinearLayoutManager(this));
        paymentDetailList.setAdapter(getAdapter());
    }

    @Override
    int contentLayout() {
        return R.layout.activity_payment_details;
    }

    Payment getPayment() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("payment");
    }
}
