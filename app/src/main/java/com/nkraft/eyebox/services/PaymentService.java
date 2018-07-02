package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.utils.HttpUtil;

import java.util.List;

public class PaymentService extends RBaseService<Payment> {

    private PaymentService() {
        super("payments.php");
    }

    private static PaymentService _instance = new PaymentService();
    public static PaymentService instance() {
        return _instance;
    }

    public PagedResult<List<Payment>> getPaymentsByUser(User user) {
        return getList(key(),
                identity(user.getId()),
                action("get"),
                HttpUtil.KeyValue.make("branch", user.getAssignedBranch()));
    }

    public PagedResult<Payment> submitPayments(List<Payment> payments) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String paymentItemValues = gson.toJson(payments);
        return postObject(
                action("submit"),
                HttpUtil.KeyValue.make("payments", paymentItemValues));
    }

    public PagedResult<List<Payment>> checkPaymentsStatus(long clientId) {
        return getList(action("status"), makeValue("clientId", clientId));
    }

}
