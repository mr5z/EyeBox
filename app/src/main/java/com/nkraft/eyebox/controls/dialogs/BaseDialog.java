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

    private AlertDialog dialog;
    private final T data;
    private Context context;

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

    BaseDialog(Context context, @Nullable T data) {
        this.data = data;
        this.context = context;
        initializeDialog(context, data, false);
    }

    BaseDialog(Context context, @Nullable T data, boolean hasCancelButton) {
        this.data = data;
        this.context = context;
        initializeDialog(context, data, hasCancelButton);
    }

    public void setOnClickListener(PositiveClickListener<T> clickListener) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getText(android.R.string.ok),
                (d, w) -> clickListener.onDialogOkay(data));
    }

    public void setOnClickListener(PositiveClickListener<T> clickListener, CharSequence proceedText) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, proceedText,
                (d, w) -> clickListener.onDialogOkay(data));
    }

    private void initializeDialog(Context context, T data, boolean hasCancelButton) {
        View view = LayoutInflater.from(context).inflate(layout(), null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setIcon(icon());
        int title = title();
        if (title > 0) {
            builder.setTitle(title);
        }
        if (hasCancelButton) {
            builder.setNegativeButton(context.getText(android.R.string.cancel),
                    (d, w) -> {});
        }
        dialog = builder.create();
        onCreateView(view, data);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
