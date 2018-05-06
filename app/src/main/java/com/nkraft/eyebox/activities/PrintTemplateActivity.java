package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PrintTemplateAdapter;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.services.AccountService;
import com.nkraft.eyebox.services.TextAlignment;
import com.nkraft.eyebox.utils.Formatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class PrintTemplateActivity extends ListActivity<PrintTemplate> implements BaseListAdapter.ItemClickListener<PrintTemplate> {

    @Override
    BaseListAdapter initializeAdapter() {
        addData(template1());
        addData(template2());
        addData(template3());
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
    String getSearchableFields(PrintTemplate printTemplate) {
        return printTemplate.getTitle();
    }

    PrintTemplate template1() {
        User user = AccountService.instance().currentUser;
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("Sale Order (Manila)", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("#12 Kabiling Street", TextAlignment.CENTER);
        template.addPrintData("Village, Camarin, Caloocan City", TextAlignment.CENTER);
        template.addPrintData("09985100306", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("Sale Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No:        " + payment.getId());
        template.addPrintData("Date:         " + dateNow());
        template.addPrintData("Client:       " + payment.getReceivedBy());
        template.addPrintData("Amount:       " + payment.getFormattedAmount());
        template.addPrintData("Total:        " + payment.getFormattedAmount());
        template.addPrintData("Received By:");
        template.addPrintData(user.getName());
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
        return  template;
    }

    PrintTemplate template2() {
        User user = AccountService.instance().currentUser;
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("SI-Felbros2", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("Sale Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No:        " + payment.getId());
        template.addPrintData("Date:         " + dateNow());
        template.addPrintData("Client:       " + payment.getReceivedBy());
        template.addPrintData("Amount:       " + payment.getFormattedAmount());
        template.addPrintData("Total:        " + payment.getFormattedAmount());
        template.addPrintData("Received By:");
        template.addPrintData(user.getName());
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
        template.addPrintData("\n");
        return  template;
    }

    PrintTemplate template3() {
        User user = AccountService.instance().currentUser;
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("Provinsional Receipt (Manila)", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("#12 Kabiling Street", TextAlignment.CENTER);
        template.addPrintData("Village, Camarin, Caloocan City", TextAlignment.CENTER);
        template.addPrintData("09985100306", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("Sale Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No:        " + payment.getId());
        template.addPrintData("Date:         " + dateNow());
        template.addPrintData("Client:       " + payment.getReceivedBy());
        template.addPrintData("Amount:       " + payment.getFormattedAmount());
        template.addPrintData("Total:        " + payment.getFormattedAmount());
        template.addPrintData("Received By:");
        template.addPrintData(user.getName());
        template.addPrintData("(Signature over print name)");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
        return  template;
    }

    Bitmap toBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int height = 64;
        int width = Math.round(height * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private Payment getPayment() {
        Intent intent = getIntent();
        return intent.getParcelableExtra("payment");
    }

    String dateNow() {
        return Formatter.date((new Date().getTime()));
    }
}
