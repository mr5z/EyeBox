package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PrintTemplateAdapter;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.models.shit.SalesReport;
import com.nkraft.eyebox.services.FontStyle;
import com.nkraft.eyebox.services.PagedResult;
import com.nkraft.eyebox.services.TextAlignment;
import com.nkraft.eyebox.utils.Formatter;
import com.nkraft.eyebox.utils.TaskWrapper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrintTemplateActivity extends ListActivity<PrintTemplate>
        implements BaseListAdapter.ItemClickListener<PrintTemplate>,
        TaskWrapper.Task<PagedResult<List<SalesReport>>> {

    private TaskWrapper<PagedResult<List<SalesReport>>> salesReportTask() {
        return new TaskWrapper<>(this);
    }

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        setPageTitle(R.string.select_template);
        salesReportTask().execute();
    }

    @Override
    BaseListAdapter initializeAdapter() {
        PrintTemplateAdapter adapter = new PrintTemplateAdapter(getDataList());
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(PrintTemplate data) {
        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra("selectedTemplate", data);
        startActivity(intent);
    }

    @Override
    String[] getSearchableFields(PrintTemplate printTemplate) {
        return new String[] { printTemplate.getTitle() };
    }

    @Override
    public void onTaskBegin() {
        showLoader(true);
    }

    @Override
    public PagedResult<List<SalesReport>> onTaskExecute() {
        List<SalesReport> salesReports = database().salesReportDao().getAllSalesReports();
        return new PagedResult<>(salesReports, salesReports.size());
    }

    @Override
    public void onTaskEnd(PagedResult<List<SalesReport>> result) {
        showLoader(false);
        if (result.isSuccess()) {
            List<PrintTemplate> printTemplates = new ArrayList<>();
            for(SalesReport salesReport : result.data) {
                printTemplates.add(toPrintTemplate(salesReport));
            }
            setDataList(printTemplates);
        }
    }

    //TODO
    private void addHeaderContent(PrintTemplate template) {
        String pixels = template.getHtmlHeader();
        if (pixels == null || pixels.length() <= 0)
            return;
        Bitmap bitmap = fromByteArrayToBitmap(pixels.getBytes());
        bitmap = resizeBitmap(bitmap);
        template.addPrintData(bitmap);
    }

    //TODO
    private void addFooterContent(PrintTemplate template) {
        String pixels = template.getHtmlFooter();
        if (pixels == null || pixels.length() <= 0)
            return;
        Bitmap bitmap = fromByteArrayToBitmap(pixels.getBytes());
        bitmap = resizeBitmap(bitmap);
        template.addPrintData(bitmap);
    }

    private void addBodyContent(PrintTemplate template) {
        List<Payment> payments = getPayments();
        String dateNow = Formatter.date((new Date().getTime()));
        Payment firstItem = payments.get(0);
        double totalAmount = 0;
        template.addPrintData("PR No:       " + firstItem.getProductNumber());
        template.addPrintData("Date:        " + dateNow);
        template.addPrintData("Client:      " + firstItem.getCustomerName());
        template.addPrintData("Check #:     " + firstItem.getCheckNo());
        template.addPrintData("----------------------", TextAlignment.CENTER);
        for(Payment payment : payments) {
            template.addPrintData("Amount:      " + payment.getFormattedAmount());
            template.addPrintData("Sales ID:    " + payment.getSalesId());
            template.addPrintData("\n");
            totalAmount += payment.getAmount();
        }
        template.addPrintData("Total: " + Formatter.currency(totalAmount), FontStyle.BOLD_MEDIUM);
    }

    private PrintTemplate toPrintTemplate(SalesReport salesReport) {
        PrintTemplate template = new PrintTemplate();
        template.setTitle(salesReport.getTitle());
        template.setBackground(fromBase64ToByteArray(salesReport.getLogo()));
        template.setHtmlHeader(salesReport.getHtmlHeader());
        template.setHtmlFooter(salesReport.getHtmlFooter());
        addBodyContent(template);
        return  template;
    }

    private byte[] fromBase64ToByteArray(String base64) {
        if (base64 == null || base64.length() == 0)
            return null;
        return Base64.decode(base64, Base64.DEFAULT);
    }

    private Bitmap fromByteArrayToBitmap(byte[] pixels) {
        return BitmapFactory.decodeByteArray(pixels, 0, pixels.length);
    }

    private byte[] fromBitmapToByArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int height = 64;
        int width = Math.round(height * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private ArrayList<Payment> getPayments() {
        Intent intent = getIntent();
        return intent.getParcelableArrayListExtra("payments");
    }
}
