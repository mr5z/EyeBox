package com.nkraft.eyebox.controls.dialogs;

import android.content.Context;
import android.support.annotation.Nullable;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.User;

public class ConfirmLogoutDialog extends BaseDialog<User> {

    public ConfirmLogoutDialog(Context context, @Nullable User data) {
        super(context, data, true);
    }

    @Override
    protected int icon() {
        return R.drawable.ic_question_mark;
    }

    @Override
    protected int title() {
        return R.string.confirm_signout;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_confirm_logout;
    }
}
