package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Visit;

public class DeleteVisitDialog extends BaseDialog<Visit> {
    public interface DeleteListener {
        void onDeleteVisit(Visit visit);
    }

    private DeleteListener deleteListener;

    public DeleteVisitDialog(Context context, @Nullable Visit data) {
        super(context, data);
    }

    @Override
    protected void onCreateView(View view, Visit data) {
        super.onCreateView(view, data);
        Button deleteButton = view.findViewById(R.id.dr_btn_delete);
        deleteButton.setOnClickListener((v) -> {
            if (deleteListener != null) {
                deleteListener.onDeleteVisit(data);
                dismiss();
            }
        });
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }


    @Override
    protected int layout() {
        return R.layout.dialog_remove_visits;
    }
}
