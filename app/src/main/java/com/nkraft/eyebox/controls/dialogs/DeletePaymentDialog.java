package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;

public class DeletePaymentDialog extends BaseDialog<Payment> {

    public interface DeleteListener {
        void onDeletePayment(Payment payment);
    }

    private DeleteListener deleteListener;

    public DeletePaymentDialog(Context context, @Nullable Payment data) {
        super(context, data);
    }

    @Override
    protected void onCreateView(View view, Payment data) {
        super.onCreateView(view, data);
        Button deleteButton = view.findViewById(R.id.dd_btn_delete);
        deleteButton.setOnClickListener((v) -> {
            if (deleteListener != null) {
                deleteListener.onDeletePayment(data);
                dismiss();
            }
        });
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_delete_payment;
    }
}
