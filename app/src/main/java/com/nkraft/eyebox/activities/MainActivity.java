package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.constants.ProcessType;
import com.nkraft.eyebox.controls.SyncDialog;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.services.ClientService;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.ProductService;
import com.nkraft.eyebox.services.TransactionService;
import com.nkraft.eyebox.utils.Debug;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SyncDialog.SyncListener {

    AccountService accountService;

    @BindView(R.id.main_txt_welcome_message)
    TextView txtWelcomeMessage;

    @BindView(R.id.main_txt_status_bar)
    TextView txtStatusBar;

    @BindView(R.id.main_txt_transactions_count)
    TextView txtTransactionsCount;

    @BindView(R.id.main_txt_payments_count)
    TextView txtPaymentsCount;

    @BindView(R.id.main_txt_products_count)
    TextView txtProductsCount;

    @BindView(R.id.main_txt_orders_count)
    TextView txtOrdersCount;

//    @OnClick({
//        R.id.btn_client_transactions,
//        R.id.btn_payments,
//        R.id.btn_clients,
//        R.id.btn_orders,
//        R.id.btn_visits,
//        R.id.btn_sign_out,
//        R.id.fab_sync,
//    })
//    void onButtonClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_client_transactions:
//                break;
//            case R.id.btn_payments:
//                break;
//            case R.id.btn_clients:
//                break;
//            case R.id.btn_orders:
//                break;
//            case R.id.btn_visits:
//                break;
//            case R.id.btn_sign_out:
//                break;
//            case R.id.fab_sync:
//                break;
//        }
//    }

    @OnClick(R.id.btn_client_transactions)
    void onTransactionsClick(View view) {
        startActivity(new Intent(this, TransactionsActivity.class));
    }

    @OnClick(R.id.btn_payments)
    void onPaymentsClick(View view) {
        startActivity(new Intent(this, PaymentsActivity.class));
    }

    @OnClick(R.id.btn_clients)
    void onClientsClick(View view) {
        startActivity(new Intent(this, ClientsActivity.class));
    }

    @OnClick(R.id.btn_orders)
    void onOrdersClick(View view) {
        startActivity(new Intent(this, OrdersActivity.class));
    }

    @OnClick(R.id.btn_visits)
    void onVisitsClick(View view) {
        startActivity(new Intent(this, VisitsActivity.class));
    }

    @OnClick(R.id.btn_sign_out)
    void onSignoutClick(View view) {
        endSession();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.fab_sync)
    void onFabClick(View view) {
        SyncDialog dialog = new SyncDialog(this);
        dialog.setSyncListener(this);
        dialog.show();
    }

    @Override
    public void onSync(int processTypes) {
        if ((processTypes & ProcessType.VISITS.flag) != 0) {
            Debug.log("sync VISITS");
        }
        if ((processTypes & ProcessType.TRANSACTIONS.flag) != 0) {
            Debug.log("sync TRANSACTIONS");
        }
        if ((processTypes & ProcessType.PAYMENTS.flag) != 0) {
            Debug.log("sync PAYMENTS");
        }
        if ((processTypes & ProcessType.ORDERS.flag) != 0) {
            Debug.log("sync ORDERS");
        }
        if ((processTypes & ProcessType.CLIENTS.flag) != 0) {
            Debug.log("sync CLIENTS");
        }
        showStatusBar(true);
        updateProgress(0, processTypes);
        performSync(processTypes);
    }

    int getSyncCount(int processTypes) {
        int count = 0;
        if ((processTypes & ProcessType.CLIENTS.flag) != 0) count ++;
        if ((processTypes & ProcessType.ORDERS.flag) != 0) count ++;
        if ((processTypes & ProcessType.PAYMENTS.flag) != 0) count ++;
        if ((processTypes & ProcessType.TRANSACTIONS.flag) != 0) count ++;
        if ((processTypes & ProcessType.VISITS.flag) != 0) count ++;
        return count;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        if (!isLoggedIn()) {
            showLoginActivity();
            finish();
            return;
        }

        accountService = AccountService.instance();

        loadCurrentUserData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showStatusBar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        async(this::updateRecordsCount);
    }

    void loadCurrentUserData() {
        async(() -> {
            long userId = settings().getUserId();
            accountService.currentUser = database().users().findUserById(userId);
            updateWelcomeText();
        });
    }

    void performSync(int processTypes) {
        async(() -> {
            ProductService productService = ProductService.instance();
            ClientService clientService = ClientService.instance();
            TransactionService transactionService = TransactionService.instance();

            User user = accountService.currentUser;
            int assignedBranch = user.getAssignedBranch();

            PagedResult<List<Product>> result1 = productService
                    .getProductsByBranch(assignedBranch);
            if (result1.isSuccess()) {
                database().products().insertProducts(result1.data);
                updateProgress(1, processTypes);
            }

            PagedResult<List<Client>> result2 = clientService
                    .getClientListByUser(user);
            if (result2.isSuccess()) {
                database().clients().insertClients(result2.data);
                updateProgress(2, processTypes);
            }

            PagedResult<List<Transaction>> result3 = transactionService
                    .getAllTransactionsByUser(user);
            if (result3.isSuccess()) {
                database().transactions().insertTransactions(result3.data);
                updateProgress(3, processTypes);
            }

            boolean success =
                result1.isSuccess() &&
                result2.isSuccess() &&
                result3.isSuccess();

            if (success) {
                updateRecordsCount();
            }

            onPostSync(success);
        });
    }


    void onPostSync(boolean success) {
        runOnUiThread(() -> showSnackbar(success ? "Sync successfully" : "Failed to sync"));
        showStatusBar(false);
    }

    void updateWelcomeText() {
        runOnUiThread(() -> {
            txtWelcomeMessage.setText(String.format("Welcome, %s", accountService.currentUser.getName()));
        });
    }

    void updateRecordsCount() {
        long transactionsCount = database().transactions().count();
        long paymentsCount = database().payments().count();
        long productsCount = database().products().count();
        long ordersCount = database().orders().count();
        updateProcessCount(ProcessType.TRANSACTIONS, transactionsCount);
        updateProcessCount(ProcessType.PAYMENTS, paymentsCount);
        updateProcessCount(ProcessType.PRODUCTS, productsCount);
        updateProcessCount(ProcessType.ORDERS, ordersCount);
    }

    void updateProgress(int current, int processTypes) {
        int total = getSyncCount(processTypes);
        setStatusMessage(String.format(Locale.getDefault(),
                "Syncing in progress (%d/%d)", current, total));
    }

    void setStatusMessage(String message) {
        runOnUiThread(() -> txtStatusBar.setText(message));
    }

    void showStatusBar(boolean show) {
        runOnUiThread(() -> txtStatusBar.setVisibility(show ? View.VISIBLE : View.GONE));
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

    void updateProcessCount(ProcessType processType, long count) {
        runOnUiThread(() -> {
            String stringCount = String.valueOf(count);
            switch (processType) {
                case TRANSACTIONS:
                    txtTransactionsCount.setText(stringCount);
                    break;
                case PAYMENTS:
                    txtPaymentsCount.setText(stringCount);
                    break;
                case PRODUCTS:
                    txtProductsCount.setText(stringCount);
                    break;
                case ORDERS:
                    txtOrdersCount.setText(stringCount);
                    break;
                case VISITS:
                    break;
            }
        });
    }
}
