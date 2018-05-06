package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;

public class RemovePaymentsDialog extends BaseDialog<Payment> {

    RemovePaymentsDialog(Context context, @Nullable Payment data) {
        super(context, data, true);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_remove_payments;
    }
}
