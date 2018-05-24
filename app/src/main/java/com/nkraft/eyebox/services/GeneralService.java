package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.models.shit.Customer;
import com.nkraft.eyebox.models.shit.CustomerAll;
import com.nkraft.eyebox.models.shit.Employee;
import com.nkraft.eyebox.models.shit.Receipt;
import com.nkraft.eyebox.models.shit.Source;
import com.nkraft.eyebox.models.shit.Table;
import com.nkraft.eyebox.models.shit.Terms;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GeneralService extends BaseService {

    private static GeneralService _instance;

    public static GeneralService instance() {
        if (_instance == null) {
            _instance = new GeneralService();
        }
        return _instance;
    }

    public PagedResult<Table> getTable() {
        AccountService accountService = AccountService.instance();
        int assignedBranch = accountService.currentUser.getAssignedBranch();
        try {
            String stringResponse = post(String.format(
                    "get_all.php?key=%s&branch=%s&username=%s",
                    API_KEY, assignedBranch, "aaronzipagan"),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONObject jsonResponse = new JSONObject(stringResponse);
            JSONArray jsonTable = jsonResponse.getJSONArray("tables");
            JSONArray jsonTerms = jsonTable.getJSONObject(0).getJSONArray("terms");
            JSONArray jsonCustomers = jsonTable.getJSONObject(1).getJSONArray("customers");
            JSONArray jsonCutomersAll = jsonTable.getJSONObject(2).getJSONArray("customers_all");
            JSONArray jsonEmployees = jsonTable.getJSONObject(3).getJSONArray("employees");
            JSONArray jsonSource = jsonTable.getJSONObject(4).getJSONArray("source");
            JSONArray jsonReceipt = jsonTable.getJSONObject(5).getJSONArray("receipt");
            JSONArray jsonBank = jsonTable.getJSONObject(6).getJSONArray("bankname");
            Table table = new Table();
            fillTerms(table, jsonTerms);
            fillCustomers(table, jsonCustomers);
            fillCustomersAll(table, jsonCutomersAll);
            fillEmployees(table, jsonEmployees);
            fillSources(table, jsonSource);
            fillReceipts(table, jsonReceipt);
            fillBanknames(table, jsonBank);
            return new PagedResult<>(table, 1);
        }
        catch (IOException | JSONException ex) {
            return new PagedResult<>(ex.getMessage());
        }
    }

    private void fillTerms(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
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
            table.addTerms(terms);
        }
    }

    private void fillCustomers(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong("customersid");
            String name = jsonObject.getString("customername");
            String address = jsonObject.getString("address");
            double totalcredit = jsonObject.getDouble("totalcredit");
            Customer customer = new Customer();
            customer.setId(id);
            customer.setName(name);
            customer.setAddress(address);
            customer.setTotalCredit(totalcredit);
            table.addCustomer(customer);
        }
    }

    private void fillCustomersAll(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong("customersid");
            String name = jsonObject.getString("customername");
            String address = jsonObject.getString("address");
            CustomerAll customerAll = new CustomerAll();
            customerAll.setId(id);
            customerAll.setName(name);
            customerAll.setAddress(address);
            table.addCustomerAll(customerAll);
        }
    }

    private void fillEmployees(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String idcode = jsonObject.getString("idcode");
            String namex = jsonObject.getString("namex");
            String middlename = jsonObject.getString("middlename");
            String nick = jsonObject.getString("nick");
            Employee employee = new Employee();
            employee.setIdcode(idcode);
            employee.setNamex(namex);
            employee.setMiddlename(middlename);
            employee.setNick(nick);
            table.addEmployee(employee);
        }
    }

    private void fillSources(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Source source = new Source();
            table.addSource(source);
        }
    }

    private void fillReceipts(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Receipt receipt = new Receipt();
        }
    }

    private void fillBanknames(Table table, JSONArray jsonArray) throws JSONException {
        for (int i = 0;i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            long company = jsonObject.getLong("company");
            String accountname = jsonObject.getString("accountname");
            String accountno = jsonObject.getString("accountno");
            String namex = jsonObject.getString("namex");
            String userlogs = jsonObject.getString("userlogs");
            int delall = jsonObject.getInt("delall");
            int branchno = jsonObject.getInt("branchno");
            String bankaddress = jsonObject.getString("bankaddress");
            Bank bank = new Bank();
            bank.setId(id);
            bank.setCompany(company);
            bank.setAccountName(accountname);
            bank.setAccountNumber(accountno);
            bank.setNamex(namex);
            bank.setUserLogs(userlogs);
            bank.setDelall(delall);
            bank.setBranchNo(branchno);
            bank.setBankAddress(bankaddress);
            table.addBank(bank);
        }
    }
}
