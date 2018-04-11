package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Client;
import com.nkraft.eyebox.models.User;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientService extends BaseService {
    private static ClientService _instance;

    public static ClientService instance() {
        if (_instance == null) {
            _instance = new ClientService();
        }
        return _instance;
    }

    public PagedResult<List<Client>> getClientListByUser(User user) {
        try {
            String stringResponse = post(String.format(
                    "eyebox/api/get_all.php?key=%s&branch=%s&username=%s",
                    API_KEY, user.getAssignedBranch(), user.getUserName()),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONObject jsonResponse = new JSONObject(stringResponse);
            JSONArray jsonTable = jsonResponse.getJSONArray("tables");
            JSONArray jsonCutomersAll = jsonTable.getJSONObject(2).getJSONArray("customers_all");
            List<Client> clientList = new ArrayList<>();
            for (int i = 0;i < jsonCutomersAll.length(); ++i) {
                JSONObject jsonObject = jsonCutomersAll.getJSONObject(i);
                long id = jsonObject.getLong("customersid");
                String name = jsonObject.getString("customername");
                String address = jsonObject.getString("address");
                Client client = new Client();
                client.setId(id);
                client.setClientName(name);
                client.setClientAddress(address);
                clientList.add(client);
            }
            return new PagedResult<>(clientList, clientList.size());
        }
        catch (IOException | JSONException e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
