package com.nkraft.eyebox.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.dao.ClientsDao;
import com.nkraft.eyebox.models.dao.OrdersDao;
import com.nkraft.eyebox.models.dao.PaymentsDao;
import com.nkraft.eyebox.models.dao.ProductsDao;
import com.nkraft.eyebox.models.dao.SalesDao;
import com.nkraft.eyebox.models.dao.TransactionsDao;
import com.nkraft.eyebox.models.dao.UserDao;
import com.nkraft.eyebox.models.dao.VisitsDao;
import com.nkraft.eyebox.models.dao.shit.BanksDao;
import com.nkraft.eyebox.models.dao.shit.TermsDao;
import com.nkraft.eyebox.services.repositories.AppDatabase;
import com.nkraft.eyebox.utils.Settings;
import com.nkraft.eyebox.utils.ViewUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    public interface ConfirmDialogListener {
        void onConfirm();
    }

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

    private Dialog _confirmDialog;
    private Dialog confirmDialog(@NonNull final ConfirmDialogListener confirmDialogListener,
                                 final String title) {
        if (_confirmDialog == null) {
            _confirmDialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setPositiveButton(R.string.proceed, (dialogInterface, i) -> {
                        confirmDialogListener.onConfirm();
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                    .setCancelable(false)
                    .create();
        }
        return _confirmDialog;
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
        new Thread(task).start();
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

    void startSession(long userId) {
        settings().setUserId(userId);
    }

    void endSession() {
        async(() -> {
            settings().clear();
            database().users().deleteAll();
            database().products().deleteAll();
            database().clients().deleteAll();
            database().transactions().deleteAll();
            database().payments().deleteAll();
            database().orders().deleteAll();
            database().sales().deleteAll();
            database().visits().deleteAll();
            database().banks().deleteAll();
            database().terms().deleteAll();
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
