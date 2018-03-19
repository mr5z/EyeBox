package com.nkraft.eyebox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.TransactionsAdapter;
import com.nkraft.eyebox.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends ListActivity {

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header(R.string.client));
        headers.add(new Header(R.string.balance));
        return headers;
    }

    @Override
    BaseListAdapter getAdapter() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction() {{ setBalance(100); setClientAddress("Client Address 1"); setClientName("Client Name 1"); }});
        transactions.add(new Transaction() {{ setBalance(100); setClientAddress("Client Address 2"); setClientName("Client Name 2"); }});
        transactions.add(new Transaction() {{ setBalance(100); setClientAddress("Client Address 3"); setClientName("Client Name 3"); }});
        return new TransactionsAdapter(transactions);
    }
}
