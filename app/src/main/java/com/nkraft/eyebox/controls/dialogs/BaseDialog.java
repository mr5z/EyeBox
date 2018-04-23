package com.nkraft.eyebox.controls.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseDialog<T> {

    public interface PositiveClickListener<T> {
        void onDialogOkay(T data);
    }

    private final AlertDialog dialog;
    private final T data;

    protected void onCreateView(View view, T data) {

    }

    @LayoutRes
    protected abstract int layout();

    @DrawableRes
    protected int icon() {
        return 0;
    }

    @StringRes
    protected int title() {
        return 0;
    }

    protected BaseDialog(Context context, @Nullable T data) {
        this.data = data;
        dialog = new AlertDialog.Builder(context).create();
        initialize(context, data, false);
    }

    protected BaseDialog(Context context, @Nullable T data, boolean hasCancelButton) {
        this.data = data;
        dialog = new AlertDialog.Builder(context).create();
        initialize(context, data, hasCancelButton);
    }

    public void setOnClickListener(PositiveClickListener<T> clickListener) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                dialog.getContext().getText(android.R.string.ok),
                (d, w) -> clickListener.onDialogOkay(data));
    }

    public void setOnClickListener(PositiveClickListener<T> clickListener, CharSequence proceedText) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                proceedText,
                (d, w) -> clickListener.onDialogOkay(data));
    }

    private void initialize(Context context, T data, boolean hasCancelButton) {
        View view = LayoutInflater.from(context).inflate(layout(), null);
        dialog.setView(view);
        dialog.setIcon(icon());
        dialog.setTitle(title());
        if (hasCancelButton) {
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    context.getText(android.R.string.cancel),
                    (d, w) -> {});
        }
        onCreateView(view, data);
    }

    public void show() {
        dialog.show();
    }
}
