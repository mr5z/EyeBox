package com.nkraft.eyebox.controls;

import android.annotation.SuppressLint;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    @SuppressLint("InflateParams")
    public TransactionDetailsDialog(Context context, Transaction transaction) {
        this.context = context;
        this.transaction = transaction;
        view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_transaction_details, null);
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.client_transaction_details)
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
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
        EditText editAmount = view.findViewById(R.id.dtd_edit_product_amount);
        Spinner spinnerBank = view.findViewById(R.id.dtd_spnr_bank_list);
        EditText editCheckDate = view.findViewById(R.id.dtd_edit_check_date);
        EditText editCheckNumber = view.findViewById(R.id.dtd_edit_check_number);
        EditText editOrderNumber = view.findViewById(R.id.dtd_edit_order_number);
        Spinner spinnerTerms = view.findViewById(R.id.dtd_spnr_terms);

        double amount = Double.parseDouble("0" + editAmount.getText().toString());
        Object objSelectedBank = spinnerBank.getSelectedItem();
        Object objSelectedTerms = spinnerTerms.getSelectedItem();
        String selectedBank = objSelectedBank != null ? objSelectedBank.toString() : "";
        String selectedTerms = objSelectedTerms != null ? objSelectedTerms.toString() : "";
        long checkDate = ((Long)editCheckDate.getTag());
        String checkNumber = editCheckNumber.getText().toString();
        String orderNumber = editOrderNumber.getText().toString();

        transaction.setBalance(amount);
        transaction.setBank(selectedBank);
        transaction.setCheckDate(checkDate);
        transaction.setCheckNumber(checkNumber);
        transaction.setOrderNumber(orderNumber);
        transaction.setTerms(selectedTerms);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.terms_list,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTerms.setAdapter(adapter);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(transaction.getCheckDate());
        editCheckDate.setOnClickListener(this);
        setCheckDate(transaction.getCheckDate());
        txtProductNumber.setText(transaction.getProductNumber());
        txtReceiver.setText(transaction.getClientName());
        editAmount.setText(String.format(Locale.getDefault(), "%.02f", transaction.getBalance()));
        editCheckNumber.setText(transaction.getCheckNumber());
        editOrderNumber.setText(transaction.getOrderNumber());
    }

    private void setCheckDate(long unixTimestamp) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(unixTimestamp);
        int year = gc.get(Calendar.YEAR);
        int month = gc.get(Calendar.MONTH);
        int day = gc.get(Calendar.DAY_OF_MONTH);
        editCheckDate.setText(String.format(
                Locale.getDefault(), "%d-%02d-%02d",
                year, month, day));
        editCheckDate.setTag((new GregorianCalendar(year, month, day)).getTimeInMillis());
    }

    private void setCheckDate(int year, int month, int day) {
        editCheckDate.setText(String.format(
                Locale.getDefault(), "%d-%02d-%02d",
                year, month, day));
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

    public void dismiss() {
        dialog.dismiss();
    }

    public void setDialogClickListener(DialogClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
