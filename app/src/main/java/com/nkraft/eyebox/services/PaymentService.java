package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.utils.HttpUtil;

import java.util.List;

public class PaymentService extends BaseService {

    private static PaymentService _instance;

    public static PaymentService instance() {
        if (_instance == null) {
            _instance = new PaymentService();
        }
        return _instance;
    }

    public PagedResult<List<Payment>> getPaymentsByUser(User user) {
        try {
            String rawResponse = get("get_payments.php",
                    HttpUtil.KeyValue.make("branch", user.getAssignedBranch()),
                    HttpUtil.KeyValue.make("username", user.getUserName()),
                    HttpUtil.KeyValue.make("key", API_KEY));
            return new PagedResult<>(rawResponse);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }

    public PagedResult<Boolean> submitPayments(Payment payment) {
        try {
            Gson gson = new Gson();
            String credits = gson.toJson(payment.getSales());
            String paymentItemValuues = gson.toJson(payment.getSales());
            String rawResponse = post("submit_payments.php",
                HttpUtil.KeyValue.make("key", API_KEY),
                HttpUtil.KeyValue.make("credits", credits),
                HttpUtil.KeyValue.make("payment_items_values", paymentItemValuues)
            );
            return new PagedResult<>(true, 1);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
