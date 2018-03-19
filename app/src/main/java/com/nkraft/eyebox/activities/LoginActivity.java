package com.nkraft.eyebox.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.nkraft.eyebox.R;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        if (isNetworkAvailable(this)) {
            showSnackbar("Connected to Internet", Snackbar.LENGTH_INDEFINITE);
        }
        else {
            showSnackbar("No Internet", Snackbar.LENGTH_INDEFINITE);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null &&
               connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @OnClick(R.id.btn_sign_in)
    void signIn(View view) {
        startSession(1);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    int contentLayout() {
        return R.layout.activity_login;
    }
}
