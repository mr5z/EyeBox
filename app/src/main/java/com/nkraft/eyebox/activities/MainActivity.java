package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.nkraft.eyebox.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @OnClick(R.id.btn_client_transactions)
    void goToTransactionsActivity(View view) {
        startActivity(new Intent(this, TransactionsActivity.class));
    }

    @OnClick(R.id.btn_payments)
    void goToPaymentsActivity(View view) {
        startActivity(new Intent(this, PaymentsActivity.class));
    }

    @OnClick(R.id.btn_clients)
    void goToProductsActivity(View view) {
        startActivity(new Intent(this, ClientsActivity.class));
    }

    @OnClick(R.id.btn_orders)
    void goToOrdersActivity(View view) {
        startActivity(new Intent(this, OrdersActivity.class));
    }

    @OnClick(R.id.btn_sign_out)
    void signOut(View view) {
        endSession();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {

        if (!isLoggedIn()) {
            showLoginActivity();
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    void showLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    int contentLayout() {
        return R.layout.activity_main;
    }
}
