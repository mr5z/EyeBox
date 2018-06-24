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

public class TransactionsActivity extends ListActivity<Transaction> implements
        TaskWrapper.Task<PagedResult<List<Transaction>>> {

    private TaskWrapper<PagedResult<List<Transaction>>> clientTask() {
        return new TaskWrapper<>(this);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.select_client_transaction);
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
    BaseListAdapter initializeAdapter() {
        TransactionsAdapter adapter = new TransactionsAdapter(getDataList());
        adapter.setOnItemClickListener(this::showDialogDetail);
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
            setDataList(result.data);
            notifyDataSetChanged();
        }
        else {
            showSnackbar(R.string.failed_loading_data);
        }
    }

    @Override
    String[] getSearchableFields(Transaction transaction) {
        return new String[] { transaction.getClientName() };
    }

    void showDialogDetail(Transaction transaction) {
        async(() -> {
            Transaction clone = cloneTransaction(transaction);
            List<Bank> bankList = database().banks().getBanksByClientId(clone.getId());
            List<Terms> termsList = database().terms().getAllTerms();
            clone.setAmount(clone.getBalance()); // haxx
            runOnUiThread(() -> {
                TransactionDetailsDialog dialog = new TransactionDetailsDialog(this, clone, bankList, termsList);
                dialog.setDialogClickListener(this::goToConfirmSalesActivity);
                dialog.show();
            });
        });
    }

    void goToConfirmSalesActivity(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            showSnackbar("Invalid amount");
            return;
        }
        Intent intent = new Intent(this, ConfirmSalesActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
    }

    Transaction cloneTransaction(Transaction transaction) {
        Transaction clone = new Transaction(transaction.getId());
        clone.setProductNumber(transaction.getProductNumber());
        clone.setCheckNumber(transaction.getCheckNumber());
        clone.setOrderNumber(transaction.getOrderNumber());
        clone.setClientAddress(transaction.getClientAddress());
        clone.setClientName(transaction.getClientName());
        clone.setProductNumber(transaction.getClientName());
        clone.setAmount(transaction.getAmount());
        clone.setBalance(transaction.getBalance());
        clone.setBank(transaction.getBank());
        clone.setTerms(transaction.getTerms());
        clone.setCheckDate(transaction.getCheckDate());
        return clone;
    }
}
