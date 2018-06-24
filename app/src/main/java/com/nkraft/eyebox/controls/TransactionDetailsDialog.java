package com.nkraft.eyebox.controls;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.utils.Formatter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class TransactionDetailsDialog implements
        Dialog.OnClickListener,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        DialogInterface.OnDismissListener {

    public interface DialogClickListener {
        void onTransactionUpdated(Transaction transaction);
    }

    private final View view;
    private AlertDialog dialog;
    private DialogClickListener clickListener;
    private EditText editCheckDate;
    private Context context;
    private Transaction transaction;
    private List<Bank> bankList;
    private List<Terms> termsList;

    public TransactionDetailsDialog(Context context, Transaction transaction, List<Bank> bankList, List<Terms> termsList) {
        this.context = context;
        this.transaction = transaction;
        this.bankList = bankList;
        this.termsList = termsList;
        view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_transaction_details, null);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.client_transaction_details)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
        // empty entities for empty selection
        bankList.add(0, new Bank() {{
            setId(-1);
        }});
        configure();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == BUTTON_POSITIVE) {
            if (clickListener != null) {
                clickListener.onTransactionUpdated(getModifiedTransaction());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dtd_edit_check_date:
            showDateTimePicker();
            break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setCheckDate(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    private Transaction getModifiedTransaction() {
        TextView txtProductNumber = view.findViewById(R.id.dtd_txt_product_number);
        EditText editAmount = view.findViewById(R.id.dtd_edit_product_amount);
        Spinner spinnerBank = view.findViewById(R.id.dtd_spnr_bank_list);
        EditText editCheckDate = view.findViewById(R.id.dtd_edit_check_date);
        EditText editCheckNumber = view.findViewById(R.id.dtd_edit_check_number);
        EditText editOrderNumber = view.findViewById(R.id.dtd_edit_order_number);
        Spinner spinnerTerms = view.findViewById(R.id.dtd_spnr_terms);

        String productNumber = txtProductNumber.getText().toString();
        double amount = Double.parseDouble("0" + editAmount.getText().toString());
        int selectedBank = spinnerBank.getSelectedItemPosition();
        int selectedTerms = spinnerTerms.getSelectedItemPosition();
        long checkDate = editCheckDate.getTag() == null ? 0 : ((Long)editCheckDate.getTag());
        String checkNumber = editCheckNumber.getText().toString();
        String orderNumber = editOrderNumber.getText().toString();

        Bank bank = bankList.get(selectedBank);
        Terms terms = termsList.get(selectedTerms);

        transaction.setProductNumber(productNumber);
        transaction.setAmount(amount);
        transaction.setBank(bank.getId());
        transaction.setCheckDate(checkDate);
        transaction.setCheckNumber(checkNumber);
        transaction.setOrderNumber(orderNumber);
        transaction.setTerms(terms.getId());

        return transaction;
    }

    private void configure() {
        TextView txtProductNumber = view.findViewById(R.id.dtd_txt_product_number);
        TextView txtReceiver = view.findViewById(R.id.dtd_txt_receiver);
        EditText editAmount = view.findViewById(R.id.dtd_edit_product_amount);
        Spinner spinnerBank = view.findViewById(R.id.dtd_spnr_bank_list);
        editCheckDate = view.findViewById(R.id.dtd_edit_check_date);
        EditText editCheckNumber = view.findViewById(R.id.dtd_edit_check_number);
        EditText editOrderNumber = view.findViewById(R.id.dtd_edit_order_number);
        Spinner spinnerTerms = view.findViewById(R.id.dtd_spnr_terms);

        ArrayAdapter<Bank> banksAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, bankList);

        ArrayAdapter<Terms> termsAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, termsList);

        banksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(banksAdapter);
        spinnerTerms.setAdapter(termsAdapter);

//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTimeInMillis(transaction.getCheckDate());
        int selectedBankIndex = getBankIndex(transaction.getBank());
        int selectedTermsIndex = getTermsIndex(transaction.getTerms());
        editCheckDate.setOnClickListener(this);
        if (selectedBankIndex >= 0)
            spinnerBank.setSelection(selectedBankIndex);
        if (selectedTermsIndex >= 0)
            spinnerTerms.setSelection(selectedTermsIndex);
        txtProductNumber.setText(randomId());
        setCheckDate(transaction.getCheckDate());
        txtReceiver.setText(transaction.getClientName());
        editAmount.setText(Formatter.currency(transaction.getAmount(), false));
        editCheckNumber.setText(transaction.getCheckNumber());
        editOrderNumber.setText(transaction.getOrderNumber());
    }

    private int getBankIndex(int bankId) {
        for (int i = 0;i < bankList.size(); ++i) {
            if (bankId == bankList.get(i).getId())
                return i;
        }
        return -1;
    }

    private int getTermsIndex(int termsId) {
        for (int i = 0;i < termsList.size(); ++i) {
            if (termsId == termsList.get(i).getId())
                return i;
        }
        return -1;
    }

    private static String randomId() {
        return UUID.randomUUID().toString().substring(0, 13).toUpperCase();
    }

    private void setCheckDate(int year, int month, int day) {
        editCheckDate.setText(String.format(
                Locale.getDefault(), "%d-%02d-%02d",
                year, month + 1, day));
        editCheckDate.setTag((new GregorianCalendar(year, month, day)).getTimeInMillis());
    }

    private void setCheckDate(long unixTimestamp) {
        if (unixTimestamp <= 0)
            return;

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(unixTimestamp);
        int year = gc.get(Calendar.YEAR);
        int month = gc.get(Calendar.MONTH);
        int day = gc.get(Calendar.DAY_OF_MONTH);
        editCheckDate.setText(String.format(
                Locale.getDefault(), "%d-%02d-%02d",
                year, month + 1, day));
        editCheckDate.setTag((new GregorianCalendar(year, month, day)).getTimeInMillis());
    }

    private void showDateTimePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setOnDismissListener(this);
        Activity activity = (Activity) context;
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    public void show() {
        dialog.show();
    }

    public void setDialogClickListener(DialogClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
