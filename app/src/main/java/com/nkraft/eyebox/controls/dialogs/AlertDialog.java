package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;

public class AlertDialog extends BaseDialog<String> {

    public AlertDialog(Context context, String data) {
        super(context, data);
    }

    @Override
    protected void onCreateView(View view, String data) {
        super.onCreateView(view, data);
        TextView txtMessage = view.findViewById(R.id.da_alert_message);
        txtMessage.setText(data);
        setOnClickListener((d) -> {

        });
    }

    @Override
    protected int icon() {
        return R.drawable.ic_alert;
    }

    @Override
    protected int title() {
        return R.string.required;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_alert;
    }
}
