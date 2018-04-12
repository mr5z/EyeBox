package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Payment;

import java.util.List;

public class PaymentService extends BaseService {

    private static PaymentService _instance;

    public static PaymentService instance() {
        if (_instance == null) {
            _instance = new PaymentService();
        }
        return _instance;
    }

    public PagedResult<List<Payment>> getPayments() {
        try {
            String rawResponse = post("");
            return new PagedResult<>(rawResponse);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
