package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.nkraft.eyebox.models.Credit;
import com.nkraft.eyebox.utils.HttpUtil;

import java.util.List;

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

    public PagedResult<Credit> submitCredits(List<Credit> credits) {
        Gson gson = new Gson();
        String postData = gson.toJson(credits);
        return postObject(action("submit"), HttpUtil.KeyValue.make("credits", postData));
    }

}
