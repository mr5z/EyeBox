package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nkraft.eyebox.models.Credit;
import com.nkraft.eyebox.utils.HttpUtil;

public class CreditService extends RBaseService<Credit> {

    private static CreditService _instance;

    public static CreditService instance() {
        if (_instance == null) {
            _instance = new CreditService();
        }
        return _instance;
    }

    private CreditService() {
        super("credits.php");
    }

    public PagedResult<Credit> submitCredit(Credit credit) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();
        String postData = gson.toJson(credit);
        return postObject(action("submit"), HttpUtil.KeyValue.make("credit", postData));
    }

}
