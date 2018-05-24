package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TermsService extends BaseService {

    private static TermsService _instance;

    public static TermsService instance() {
        if (_instance == null) {
            _instance = new TermsService();
        }
        return _instance;
    }
    public PagedResult<List<Terms>> getTerms() {
        try {
            String rawResponse = get("get_terms.php",
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONArray jsonArray = new JSONArray(rawResponse);
            List<Terms> termsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int days = jsonObject.getInt("days");
                String namex = jsonObject.getString("namex");
                int dateDelay = jsonObject.getInt("datedelay");
                Terms terms = new Terms();
                terms.setId(id);
                terms.setDays(days);
                terms.setNamex(namex);
                terms.setDateDelay(dateDelay);
                termsList.add(terms);
            }
            return new PagedResult<>(termsList, termsList.size());
        } catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
