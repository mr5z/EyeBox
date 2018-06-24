package com.nkraft.eyebox.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.services.LogService;
import com.nkraft.eyebox.services.repositories.AppDatabase;
import com.nkraft.eyebox.utils.Settings;
import com.nkraft.eyebox.utils.ViewUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    public interface ConfirmDialogListener {
        void onConfirm();
    }

    static ThreadPoolExecutor threadPoolExecutor =
            (ThreadPoolExecutor)Executors.newFixedThreadPool(3);

    public AppDatabase database() {
        return AppDatabase.instance(this);
    }

    Settings settings() {
        return Settings.instance(this);
    }

    ViewUtils views() {
        return ViewUtils.instance(this);
    }

    @ColorInt
    int color(@ColorRes int resourceId) {
        return ContextCompat.getColor(this, resourceId);
    }

    private Snackbar _snackbar;
    private Snackbar snackbar() {
        return snackbar(Snackbar.LENGTH_LONG);
    }
    private Snackbar snackbar(int duration) {
        if (_snackbar == null) {
            _snackbar = Snackbar.make(findViewById(android.R.id.content), "", duration);
        }
        _snackbar.setDuration(duration).dismiss();
        return _snackbar;
    }

    private ProgressDialog _loader;
    private ProgressDialog loader() {
        if (_loader == null) {
            _loader = new ProgressDialog(this);
            _loader.setCancelable(false);
            _loader.setOnCancelListener(dialogInterface -> {
                _loader.dismiss();
                finish();
            });
        }
        return _loader;
    }

    private Toast _toast;
    @SuppressLint("ShowToast")
    private Toast toast(String message, int duration) {
        if (_toast != null) {
            _toast.cancel();
        }
        _toast = Toast.makeText(this, message, duration);
        return _toast;
    }

    protected void showToast(String message) {
        toast(message, Toast.LENGTH_LONG).show();
    }

    private Dialog confirmDialog(@NonNull final ConfirmDialogListener confirmDialogListener,
                                 final String title) {
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton(R.string.proceed, (dialogInterface, i) -> {
                    confirmDialogListener.onConfirm();
                    dialogInterface.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .create();
    }

    private Dialog alertDialog(final String title, final String message, @DrawableRes final int icon) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        if (icon != 0) {
            dialog.setIcon(icon);
        }
        return dialog;
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentLayout());
        ButterKnife.bind(this);
        initialize(savedInstanceState);
        setTitle(contentTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setPageTitle(@StringRes int resId) {
        setPageTitle(getString(resId));
    }

    void setPageTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    void showLoader(boolean show, String message) {
        loader().setMessage(message);
        showLoader(show);
    }

    void showLoader(boolean show, int titleId) {
        loader().setTitle(titleId);
        showLoader(show);
    }

    void showLoader(boolean show) {
        if (show) {
            loader().show();
        }
        else {
            loader().dismiss();
        }
    }

    void popToRoot(Class<? extends Activity> rootActivity) {
        Intent intent = new Intent(this, rootActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    void popToMainActivity() {
        popToRoot(MainActivity.class);
    }

    void async(Runnable task) {
        threadPoolExecutor.execute(task);
    }

    void showSnackbar(String message) {
        snackbar().setText(message).show();
    }

    void showSnackbar(String message, String action, View.OnClickListener clickListener) {
        snackbar(Snackbar.LENGTH_LONG)
                .setAction(action, clickListener)
                .show();
    }

    void showSnackbar(String message, Object ...args) {
        snackbar().setText(String.format(message, args)).show();
    }

    void showSnackbar(String message, int duration) {
        Snackbar snackbar = snackbar(duration).setText(message);
        centerText(snackbar);
        snackbar.show();
    }

    void showSnackbar(int message, int duration) {
        snackbar(duration).setText(message).show();
    }

    void showSnackbar(int message) {
        snackbar(Snackbar.LENGTH_LONG).setText(message).show();
    }

    void centerText(Snackbar snackbar) {
        View view = snackbar.getView();
        TextView mTextView = view.findViewById(android.support.design.R.id.snackbar_text);
        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    void showConfirmDialog(ConfirmDialogListener confirmDialogListener, String title) {
        confirmDialog(confirmDialogListener, title)
                .show();
    }

    void showAlertDialog(String title, String message) {
        alertDialog(title, message, 0)
                .show();
    }

    void showAlertDialog(String title, String message, @DrawableRes int icon) {
        alertDialog(title, message, icon);
    }

    void startSession(long userId) {
        settings().setUserId(userId);
    }

    void endSession() {
        async(() -> {
            settings().clear();
            database().clearAllTables();
        });
    }

    void uploadLog(String message, Object ...args) {
        async(() -> {
            User currentUser = AccountService.instance().currentUser;
            LogService.instance().uploadLog(currentUser.getId(), String.format(message, args));
        });
    }

    boolean isLoggedIn() {
        return settings().isLoggedIn();
    }

    void initialize(@Nullable Bundle savedInstanceState) { }

    @LayoutRes
    abstract int contentLayout();

    @StringRes
    int contentTitle() {
        return R.string.app_name;
    }
}
