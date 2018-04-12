package com.nkraft.eyebox.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.utils.TaskWrapper;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements TaskWrapper.Task<PagedResult<User>> {

    private AccountService accountService;

    TaskWrapper<PagedResult<User>> loginTask() {
        return new TaskWrapper<>(this);
    }

    @Override
    public void onTaskBegin() {
        showLoader(true, R.string.signing_in);
    }

    @Override
    public PagedResult<User> onTaskExecute() {
        EditText txtUserName = findViewById(R.id.txt_username);
        EditText txtPassword = findViewById(R.id.txt_password);
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        return accountService.login(userName, password);
    }

    @Override
    public void onTaskEnd(PagedResult<User> result) {
        showLoader(false);
        if (result.isSuccess()) {
            accountService.currentUser = result.data;
            updateUserDb(result.data);
            startSession(result.data.getId());
            goToMainPage();
        }
        else {
            showSnackbar(result.errorMessage);
        }
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        accountService = AccountService.instance();

        if (isNetworkAvailable(this)) {
            showSnackbar("Connected to Internet", Snackbar.LENGTH_INDEFINITE);
        }
        else {
            showSnackbar("No Internet", Snackbar.LENGTH_INDEFINITE);
        }
    }

    void updateUserDb(User user) {
        async(() -> database().users().insertUser(user));
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null &&
               connectivityManager.getActiveNetworkInfo().isConnected();
    }

    void goToMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_sign_in)
    void onSignIn(View view) {
        loginTask().execute();
    }

    @Override
    int contentLayout() {
        return R.layout.activity_login;
    }
}
