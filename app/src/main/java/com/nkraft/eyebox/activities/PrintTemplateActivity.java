package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PrintTemplateAdapter;
import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.PrintTemplate;
import com.nkraft.eyebox.services.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class PrintTemplateActivity extends ListActivity implements BaseListAdapter.ItemClickListener<PrintTemplate> {

    @Override
    BaseListAdapter buildAdapter() {
        List<PrintTemplate> dataList = new ArrayList<>();
        dataList.add(template1());
        dataList.add(template2());
        dataList.add(template3());
        PrintTemplateAdapter adapter = new PrintTemplateAdapter(dataList);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(PrintTemplate data) {
        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra("selectedTemplate", data);
        startActivity(intent);
    }

    PrintTemplate template1() {
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("Sales Order (Manila)", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("#12 Kabiling Street", TextAlignment.CENTER);
        template.addPrintData("Village, Camarin, Caloocan City", TextAlignment.CENTER);
        template.addPrintData("09985100306", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("Sales Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No: " + payment.getProductNumber());
        template.addPrintData("Date:    2018-03-29");
        template.addPrintData("Client: " + payment.getClientName());
        template.addPrintData("Amount: " + payment.getFormattedTotalPayment());
        template.addPrintData("Total: " + payment.getFormattedTotalPayment());
        template.addPrintData("Received By:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
        template.addPrintData("\n");
        return  template;
    }

    PrintTemplate template2() {
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("SI-Felbros2", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("Sales Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No: " + payment.getProductNumber());
        template.addPrintData("Date:    2018-03-29");
        template.addPrintData("Client: " + payment.getClientName());
        template.addPrintData("Amount: " + payment.getFormattedTotalPayment());
        template.addPrintData("Total: " + payment.getFormattedTotalPayment());
        template.addPrintData("Received By:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
        template.addPrintData("\n");
        return  template;
    }

    PrintTemplate template3() {
        Payment payment = getPayment();
        PrintTemplate template = new PrintTemplate("Provinsional Receipt (Manila)", R.drawable.ic_tgp);
        template.addPrintData(toBitmap(R.drawable.ic_tgp));
        template.addPrintData("#12 Kabiling Street", TextAlignment.CENTER);
        template.addPrintData("Village, Camarin, Caloocan City", TextAlignment.CENTER);
        template.addPrintData("09985100306", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("Sales Order", TextAlignment.CENTER);
        template.addPrintData("\n");
        template.addPrintData("PR No: " + payment.getProductNumber());
        template.addPrintData("Date:    2018-03-29");
        template.addPrintData("Client: " + payment.getClientName());
        template.addPrintData("Amount: " + payment.getFormattedTotalPayment());
        template.addPrintData("Total: " + payment.getFormattedTotalPayment());
        template.addPrintData("Received By:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("Payor:");
        template.addPrintData("________________________");
        template.addPrintData("(Signature over print name)");
        template.addPrintData("\n");
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

}
