package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;

import com.nkraft.eyebox.R;

public class PaymentAddedDialog extends BaseDialog<Object> {
    public PaymentAddedDialog(Context context) {
        super(context, null);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_payment_added;
    }

    @Override
    protected int icon() {
        return R.drawable.ic_check;
    }

    @Override
    protected int title() {
        return R.string.payment_added;
    }
}
