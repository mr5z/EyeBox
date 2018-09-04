package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.constants.ProcessType;
import com.nkraft.eyebox.controls.SyncDialog;
import com.nkraft.eyebox.controls.dialogs.ConfirmLogoutDialog;
import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.Credit;
import com.nkraft.eyebox.models.Order;
import com.nkraft.eyebox.models.Order2;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.models.Sale;
import com.nkraft.eyebox.models.Transaction;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.models.Visit;
import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.SalesReport;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.services.BankService;
import com.nkraft.eyebox.services.ClientService;
import com.nkraft.eyebox.services.CreditService;
import com.nkraft.eyebox.services.OrderService;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.PaymentService;
import com.nkraft.eyebox.services.ProductService;
import com.nkraft.eyebox.services.SalesService;
import com.nkraft.eyebox.services.SalesReportService;
import com.nkraft.eyebox.services.TermsService;
import com.nkraft.eyebox.services.TransactionService;
import com.nkraft.eyebox.services.VisitService;
import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.Formatter;
import com.nkraft.eyebox.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SyncDialog.SyncListener {

    private AccountService accountService = AccountService.instance();

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

    @BindView(R.id.fab_sync)
    FloatingActionButton fabSync;

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
        User user = accountService.currentUser;
        ConfirmLogoutDialog dialog = new ConfirmLogoutDialog(this, user);
        dialog.setOnClickListener((u) -> {
            endSession();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        dialog.show();
    }

    @OnClick(R.id.fab_sync)
    void onFabClick(View view) {
        SyncDialog dialog = new SyncDialog(this);
        dialog.setSyncListener(this);
        dialog.show();
    }

    @Override
    public void onSync(int processTypes) {
        startAnimateSync();

        if (hasFlag(processTypes, ProcessType.TRANSACTIONS)) {
            processTypes |= ProcessType.BANKS.flag;
            processTypes |= ProcessType.TERMS.flag;
            processTypes |= ProcessType.CLIENTS.flag;
            processTypes |= ProcessType.SALES_REPORT.flag;
        }

        if (hasFlag(processTypes, ProcessType.SUBMIT_PAYMENTS)) {
            processTypes |= ProcessType.CREDITS.flag;
        }

        if (hasFlag(processTypes, ProcessType.VISITS)) {
            Debug.log("sync VISITS");
        }
        if (hasFlag(processTypes, ProcessType.TRANSACTIONS)) {
            Debug.log("sync TRANSACTIONS");
        }
        if (hasFlag(processTypes, ProcessType.SUBMIT_PAYMENTS)) {
            Debug.log("sync SUBMIT_PAYMENTS");
        }
        if (hasFlag(processTypes, ProcessType.ORDERS)) {
            Debug.log("sync ORDERS");
        }
        if (hasFlag(processTypes, ProcessType.CLIENTS)) {
            Debug.log("sync CLIENTS");
        }
        if (hasFlag(processTypes, ProcessType.PRODUCTS)) {
            Debug.log("sync PRODUCTS");
        }
        if (hasFlag(processTypes, ProcessType.SALES)) {
            Debug.log("sync SALES");
        }
        if (hasFlag(processTypes, ProcessType.SALES_REPORT)) {
            Debug.log("sync SALES_REPORT");
        }
        showStatusBar(true);
        updateProgress(0, processTypes);
        performSync(processTypes);
    }

    void startAnimateSync() {
        int fiveMinutes = 60 * 1000 * 5;
        ViewCompat.animate(fabSync)
                .rotation((fiveMinutes / 1000 / 5) * 360)
                .withLayer()
                .setDuration(fiveMinutes)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    void stopAnimateSync() {
        ViewCompat.animate(fabSync)
                .cancel();
    }

    int getSyncCount(int processTypes) {
        int count = 0;
        for(int i = 0;i < ProcessType.values().length; ++i) {
            int flag = ProcessType.values()[i].flag;
            count = count + (((processTypes & flag) != 0) ? 1 : 0);
        }
        return count;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        if (!isLoggedIn()) {
            showLoginActivity();
            finish();
            return;
        }

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
            updateWelcomeText(accountService.currentUser);
        });
    }

    //TODO fix this
    void performSync(int processTypes) {
        async(() -> {

            User user = accountService.currentUser;
            int assignedBranch = user.getAssignedBranch();

            boolean success = true;
            int progress = 0;

            if (hasFlag(processTypes, ProcessType.BANKS)) {
                BankService bankService = BankService.instance();
                PagedResult<List<Bank>> result = bankService.getBanks(user.getAssignedBranch());
                if (result.isSuccess()) {
                    database().banks().deleteAll();
                    database().banks().insertBanks(result.data);
                    updateProgress(++progress, processTypes);
                } else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.TERMS)) {
                TermsService termsService = TermsService.instance();
                PagedResult<List<Terms>> result = termsService.getTerms();
                if (result.isSuccess()) {
                    database().terms().deleteAll();
                    database().terms().insertTerms(result.data);
                    updateProgress(++progress, processTypes);
                } else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.PRODUCTS)) {
                ProductService productService = ProductService.instance();
                PagedResult<List<Product>> result = productService
                        .getProductsByBranch(assignedBranch);
                if (result.isSuccess()) {
                    database().products().deleteAll();
                    database().products().insertProducts(result.data);
                    updateProgress(++progress, processTypes);
                }
                else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.CLIENTS)) {
                ClientService clientService = ClientService.instance();
                PagedResult<List<Client>> result = clientService
                        .getClientListByUser(user);
                if (result.isSuccess()) {
                    database().clients().deleteAll();
                    database().clients().insertClients(result.data);
                    updateProgress(++progress, processTypes);
                }
                else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.SUBMIT_PAYMENTS)) {
                PaymentService paymentService = PaymentService.instance();
                List<Payment> payments = database().payments().getAllUnsubmittedPayments();
                if (!payments.isEmpty()) {
                    PagedResult<Payment> result = paymentService.submitPayments(payments);
                    if (result.isSuccess()) {
                        long affectedRows = database().payments().markAllSubmitted();
                        updateProgress(++progress, processTypes);
                    } else {
                        success = false;
                    }
                }
                else {
                    updateProgress(++progress, processTypes);
                }
            }

            if (hasFlag(processTypes, ProcessType.TRANSACTIONS)) {
                TransactionService transactionService = TransactionService.instance();
                PagedResult<List<Transaction>> result = transactionService
                        .getAllTransactionsByUser(user);
                if (result.isSuccess()) {
                    database().transactions().deleteAll();
                    database().transactions().insertTransactions(result.data);
                    updateProgress(++progress, processTypes);
                }
            }

            if (hasFlag(processTypes, ProcessType.SALES)) {
                SalesService salesService = SalesService.instance();
                PagedResult<List<Sale>> result = salesService.getSalesByBranch(user.getAssignedBranch());
                if (result.isSuccess()) {
                    for (Sale sale : result.data) {
                        if (sale.getTransaction() <= 0) {
                            Debug.log("SO# is zero");
                        }
                    }
                    database().sales().deleteAll();
                    database().sales().insertSales(result.data);
                    updateProgress(++progress, processTypes);
                }
                else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.SUBMIT_ORDERS)) {
                OrderService orderService = OrderService.instance();
                List<Order2> orders = database().orders2().getAllOrders();
                if (!orders.isEmpty()) {
                    PagedResult<Order2> result = orderService.submitOrders(orders);
                    if (result.isSuccess()) {
                        database().orders2().deleteAll();
                        database().orders().deleteAll();
                        updateProgress(++progress, processTypes);
                    }
                    else {
                        success = false;
                    }
                }
                else {
                    updateProgress(++progress, processTypes);
                }
            }

            if (hasFlag(processTypes, ProcessType.CREDITS)) {
                CreditService creditService = CreditService.instance();
                List<Credit> credits = database().credits().getAllCredits();
                if (!credits.isEmpty()) {
                    PagedResult<Credit> result = creditService.submitCredits(credits);
                    if (result.isSuccess()) {
                        database().credits().deleteAll();
                        updateProgress(++progress, processTypes);
                    } else {
                        success = false;
                    }
                }
                else {
                    updateProgress(++progress, processTypes);
                }
            }

            if (hasFlag(processTypes, ProcessType.SALES_REPORT)) {
                SalesReportService salesReportService = SalesReportService.instance();
                PagedResult<List<SalesReport>> result = salesReportService.getAllSalesReport();
                if (result.isSuccess()) {
                    uploadLog("successfully synced SALES_REPORT. data size: %d", result.data.size());
                    database().salesReportDao().deleteAll();
                    database().salesReportDao().insertSalesReport(result.data);
                    updateProgress(++progress, processTypes);
                }
                else {
                    success = false;
                }
            }

            if (hasFlag(processTypes, ProcessType.VISITS)) {
                VisitService visitService = VisitService.instance();
                List<Visit> visits = database().visits().getAllVisits();
                if (!visits.isEmpty()) {
                    PagedResult<Visit> result = visitService.submitVisits(visits);
                    if (result.isSuccess()) {
                        uploadVisits(visits);
                        updateProgress(++progress, processTypes);
                    } else {
                        success = false;
                    }
                }
                else {
                    updateProgress(++progress, processTypes);
                }
            }

            if (success) {
                updateRecordsCount();
            }

            onPostSync(success);
        });
    }

    private void uploadVisits(List<Visit> visits) {
        async(() -> {
            VisitService visitService = VisitService.instance();
            boolean success = true;
            for(Visit visit : visits) {
                File file = new File(visit.getFileName());
                if (!file.exists())
                    continue;
                String path = visitService.getServicePath() + "?action=upload";
                try {
                    String response = HttpUtil.uploadFile(path,
                        "signature",
                        file,
                        HttpUtil.KeyValue.make("visitId", visit.getId())
                    );
                    Debug.log("upload response: %s", response);
                } catch (IOException e) {
                    Debug.log("exception caught: %s", e.getMessage());
                    success = false;
                }
            }
            if (success) {
                database().visits().deleteAll();
            }
        });
    }

    void onPostSync(boolean success) {
        runOnUiThread(() -> {
            showSnackbar(success ? "Sync successfully" : "Failed to sync");
            stopAnimateSync();
        });
        showStatusBar(false);
    }

    void updateWelcomeText(User user) {
        runOnUiThread(() ->
                txtWelcomeMessage.setText(
                        Formatter.string("Welcome, %s", user.getName())));
    }

    boolean hasFlag(int processTypes, ProcessType flag) {
        return ProcessType.hasFlag(processTypes, flag);
    }

    void updateRecordsCount() {
        long transactionsCount = database().transactions().count();
        long paymentsCount = database().payments().count();
        long productsCount = database().products().count();
        long ordersCount = database().orders().count();
        updateProcessCount(ProcessType.TRANSACTIONS, transactionsCount);
        updateProcessCount(ProcessType.SUBMIT_PAYMENTS, paymentsCount);
        updateProcessCount(ProcessType.PRODUCTS, productsCount);
        updateProcessCount(ProcessType.ORDERS, ordersCount);
    }

    void updateProgress(int current, int processTypes) {
        int total = getSyncCount(processTypes);
        setStatusMessage(Formatter.string(
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
                case SUBMIT_PAYMENTS:
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
