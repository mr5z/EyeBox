package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService extends BaseService {

    private static TransactionService _instance;

    public static TransactionService instance() {
        if (_instance == null) {
            _instance = new TransactionService();
        }
        return _instance;
    }

    public PagedResult<List<Transaction>> getAllTransactionsByUser(User user) {
        try {
            String rawResponse = get("get_transactions.php",
                    HttpUtil.KeyValue.make("branch", user.getAssignedBranch()),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONArray jsonArray = new JSONArray(rawResponse);
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("customersid");
                String name = jsonObject.getString("customername");
                String address = jsonObject.getString("address");
                double totalCredit = jsonObject.getDouble("totalcredit");
                Transaction transaction = new Transaction(id);
                transaction.setClientName(name);
                transaction.setClientAddress(address);
                transaction.setBalance(totalCredit);
                transactions.add(transaction);
            }
            return new PagedResult<>(transactions, transactions.size());
        }
        catch (IOException | JSONException e) {
            return new PagedResult<>(e.getMessage());
        }
    }

}
