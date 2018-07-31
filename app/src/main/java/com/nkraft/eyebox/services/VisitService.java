package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nkraft.eyebox.models.Visit;
import com.nkraft.eyebox.utils.HttpUtil;

import java.util.List;

public class VisitService extends RBaseService<Visit> {

    private static VisitService _instance = new VisitService();

    public static VisitService instance() {
        return _instance;
    }

    private VisitService() {
        super("visits.php");
    }

    public PagedResult<List<Visit>> getVisits(long employeeId) {
        throw new UnsupportedOperationException("getVisits");
    }

    public PagedResult<Visit> submitVisits(List<Visit> visits) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String postData = gson.toJson(visits);
        return postObject(action("submit"),
                makeValue("visits", postData));
    }
}
