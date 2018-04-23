package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.TransactionsAdapter;
import com.nkraft.eyebox.controls.TransactionDetailsDialog;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends ListActivity implements
        TaskWrapper.Task<PagedResult<List<Transaction>>>,
        BaseListAdapter.ItemClickListener<Transaction>,TransactionDetailsDialog.DialogClickListener {

    private TaskWrapper<PagedResult<List<Transaction>>> clientTask() {
        return new TaskWrapper<>(this);
    }

    private TransactionsAdapter adapter;
    private List<Transaction> transactions = new ArrayList<>();
    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        clientTask().execute();
    }

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header(R.string.client));
        headers.add(new Header(R.string.balance));
        return headers;
    }

    @Override
    BaseListAdapter buildAdapter() {
        adapter = new TransactionsAdapter(transactions);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onTaskBegin() {
        showLoader(true);
    }

    @Override
    public PagedResult<List<Transaction>> onTaskExecute() {
        List<Transaction> transactions = database().transactions().getAllTransactions();
        return new PagedResult<>(transactions, transactions.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<Transaction>> result) {
        showLoader(false);
        if (result.isSuccess()) {
            transactions.clear();
            transactions.addAll(result.data);
            adapter.notifyDataSetChanged();
        }
        else {
            showSnackbar(R.string.failed_loading_data);
        }
    }

    @Override
    public void onItemClick(Transaction data) {
        showDialogDetail(data);
    }

    @Override
    public void onTransactionUpdated(Transaction transaction) {
        goToConfirmSalesActivity(transaction);
    }

    void showDialogDetail(Transaction transaction) {
        async(() -> {
            List<Bank> bankList = database().banks().getAllBanks();
            List<Terms> termsList = database().terms().getAllTerms();

            runOnUiThread(() -> {
                TransactionDetailsDialog dialog = new TransactionDetailsDialog(this, transaction, bankList, termsList);
                dialog.setDialogClickListener(this);
                dialog.show();
            });
        });
    }

    void goToConfirmSalesActivity(Transaction transaction) {
        Intent intent = new Intent(this, ConfirmSalesActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
    }
}
