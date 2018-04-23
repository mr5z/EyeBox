package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.shit.Bank;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankService extends BaseService {

    private static BankService _instance;

    public static BankService instance() {
        if (_instance == null) {
            _instance = new BankService();
        }
        return _instance;
    }

    public PagedResult<List<Bank>> getBanks(int branchNumber) {
        try {
            String rawResponse = get("get_banks.php",
                    HttpUtil.KeyValue.make("key", API_KEY),
                    HttpUtil.KeyValue.make("branch", branchNumber));
            JSONArray jsonArray = new JSONArray(rawResponse);
            List<Bank> bankList = new ArrayList<>();
            for(int i = 0;i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String company = jsonObject.getString("company");
                String accountName = jsonObject.getString("accountname");
                String accountno = jsonObject.getString("accountno");
                String namex = jsonObject.getString("namex");
                String userlogs = jsonObject.getString("userlogs");
                int delall = jsonObject.getInt("delall");
                int branchno = jsonObject.getInt("branchno");
                String bankaddress = jsonObject.getString("bankaddress");
                Bank bank = new Bank();
                bank.setId(id);
                bank.setCompany(company);
                bank.setAccountNumber(accountName);
                bank.setAccountNumber(accountno);
                bank.setNamex(namex);
                bank.setUserLogs(userlogs);
                bank.setDelall(delall);
                bank.setBranchNo(branchno);
                bank.setBankAddress(bankaddress);
                bankList.add(bank);
            }
            return new PagedResult<>(bankList, bankList.size());
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
