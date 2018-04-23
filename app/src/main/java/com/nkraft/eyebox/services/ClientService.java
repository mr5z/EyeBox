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
            String stringResponse = get("get_customers.php",
                    HttpUtil.KeyValue.make("branch", user.getAssignedBranch()),
                    HttpUtil.KeyValue.make("username", user.getUserName()),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONArray jsonArray = new JSONArray(stringResponse);
            List<Client> clientList = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("customersid");
                String name = jsonObject.getString("customername");
                String address = jsonObject.getString("address");
                String contactperson = jsonObject.getString("contactperson");
                String contactno = jsonObject.getString("contactno");
                String emailx = jsonObject.getString("emailx");
                double creditlimit = jsonObject.getDouble("creditlimit");
                int terms = jsonObject.getInt("terms");
                String datex = jsonObject.getString("datex");
                int delall = jsonObject.getInt("delall");
                String userlogs = jsonObject.getString("userlogs");
                int branchlink = jsonObject.getInt("branchlink");
                String salesdistrict = jsonObject.getString("salesdistrict");
                String salesdivision = jsonObject.getString("salesdivision");
                String districtmanager = jsonObject.getString("districtmanager");
                String salesmanager = jsonObject.getString("salesmanager");
                String salesagent = jsonObject.getString("salesagent");
                double totalcredit = jsonObject.getDouble("totalcredit");
                String tinx = jsonObject.getString("tinx");
                String totalsales = jsonObject.getString("totalsales");
                String adjustmentid = jsonObject.getString("adjustmentid");
                String addon = jsonObject.getString("addon");
                String agent = jsonObject.getString("agent");
                String checkedby = jsonObject.getString("checkedby");
                String deliveredby = jsonObject.getString("deliveredby");
                String salestype = jsonObject.getString("salestype");
                String salesaccount = jsonObject.getString("salesaccount");
                String blocked = jsonObject.getString("blocked");
                String password = jsonObject.getString("password");
                String username = jsonObject.getString("username");
                String duerestrict = jsonObject.getString("duerestrict");
                String creditrestrict = jsonObject.getString("creditrestrict");
                String dateclosed = jsonObject.getString("dateclosed");
                String status = jsonObject.getString("status");
                String remarks = jsonObject.getString("remarks");
                String creditext = jsonObject.getString("creditext");
                String mobileno = jsonObject.getString("mobileno");
                Client client = new Client();
                client.setId(id);
                client.setName(name);
                client.setAddress(address);
                client.setContactPerson(contactperson);
                client.setContactNo(contactno);
                client.setEmailX(emailx);
                client.setCreditLimit(creditlimit);
                client.setTerms(terms);
                client.setDateX(datex);
                client.setDelAll(delall);
                client.setUserLogs(userlogs);
                client.setBranchLink(branchlink);
                client.setSalesDistrict(salesdistrict);
                client.setSalesDivision(salesdivision);
                client.setDistrictManager(districtmanager);
                client.setSalesManager(salesmanager);
                client.setSalesAgent(salesagent);
                client.setTotalCredit(totalcredit);
                client.setTinX(tinx);
                client.setTotalSales(totalsales);
                client.setAdjustmentId(adjustmentid);
                client.setAddOn(addon);
                client.setAgent(agent);
                client.setCheckedBy(checkedby);
                client.setDeliveredBy(deliveredby);
                client.setSalesType(salestype);
                client.setSalesAccount(salesaccount);
                client.setBlocked(blocked);
                client.setPassword(password);
                client.setUsername(username);
                client.setDueRestrict(duerestrict);
                client.setCreditRestrict(creditrestrict);
                client.setDateClosed(dateclosed);
                client.setStatus(status);
                client.setRemarks(remarks);
                client.setCreditExt(creditext);
                client.setMobileNo(mobileno);
                clientList.add(client);
            }
            return new PagedResult<>(clientList, clientList.size());
        }
        catch (IOException | JSONException e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
