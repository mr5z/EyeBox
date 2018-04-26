package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Payment;
import com.nkraft.eyebox.models.Sale;
import com.nkraft.eyebox.utils.Debug;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesService extends BaseService {

    private static SalesService _instance;

    public static SalesService instance() {
        if (_instance == null) {
            _instance = new SalesService();
        }
        return _instance;
    }

    public PagedResult<List<Sale>> getSalesByBranch(int branchNumber) {
        try {
            String rawResponse = get("get_sales.php",
                    HttpUtil.KeyValue.make("branch", branchNumber),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONArray jsonArray = new JSONArray(rawResponse);
            List<Sale> sales = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("salesidx");
                long customerid = jsonObject.getLong("customerid");
                String companyname = jsonObject.getString("companyname");
                long agent = jsonObject.getLong("agent");
                String checkedby = jsonObject.getString("checkedby");
                long deliveredby = jsonObject.getLong("deliveredby");
                String adjustments = jsonObject.getString("adjustments");
                String adjustmentamount = jsonObject.getString("adjustmentamount");
                String userlogs = jsonObject.getString("userlogs");
                String delall = jsonObject.getString("delall");
                String drno = jsonObject.getString("drno");
                String sino = jsonObject.getString("sino");
                String prno = jsonObject.getString("prno");
                String orno = jsonObject.getString("orno");
                double amount = jsonObject.getDouble("amount");
                double totalamount = jsonObject.getDouble("totalamount");
                String salestype = jsonObject.getString("salestype");
                String salesaccount = jsonObject.getString("salesaccount");
                String remarks = jsonObject.getString("remarks");
                double origtotal = jsonObject.getDouble("origtotal");
                int branchno = jsonObject.getInt("branchno");
                double debit = jsonObject.getDouble("debit");
                double payamount = jsonObject.getDouble("payamount");
                double balance = jsonObject.getDouble("balance");
                String syncx = jsonObject.getString("syncx");
                String modified = jsonObject.getString("modified");
                double transaction = jsonObject.getDouble("transaction");
                String blocked = jsonObject.getString("blocked");
                int terms = jsonObject.getInt("terms");
                String printx = jsonObject.getString("printx");
                int printlayout = jsonObject.getInt("printlayout");
                String audit = jsonObject.getString("audit");
                String paymentmethod = jsonObject.getString("paymentmethod");
                String notification = jsonObject.getString("notification");
                long salesdate = jsonObject.getLong("salesdate");
                long datemodified = jsonObject.getLong("datemodified");
                long deliverydate = jsonObject.getLong("deliverydate");
                long duedate = jsonObject.getLong("duedate");
                long datedeposit = jsonObject.getLong("datedeposit");
                long checkdate = jsonObject.getLong("checkdate");
                int itemcount = jsonObject.getInt("itemcount");
                Sale sale = new Sale(id);
                sale.setCustomerId(customerid);
                sale.setCompanyName(companyname);
                sale.setAgent(agent);
                sale.setCheckedBy(checkedby);
                sale.setDeliveredBy(deliveredby);
                sale.setAdjustments(adjustments);
                sale.setAdjustmentAmount(adjustmentamount);
                sale.setUserLogs(userlogs);
                sale.setDelAll(delall);
                sale.setDrNo(drno);
                sale.setSiNo(sino);
                sale.setPrNo(prno);
                sale.setOrNo(orno);
                sale.setAmount(amount);
                sale.setTotalAmount(totalamount);
                sale.setSalesDate(salesdate);
                sale.setRemarks(remarks);
                sale.setOrigTotal(origtotal);
                sale.setBranchNo(branchno);
                sale.setDebit(debit);
                sale.setPayAmount(payamount);
                sale.setBalance(amount); // TODO this was previously 'balance'
                sale.setSyncX(syncx);
                sale.setModified(modified);
                sale.setDateModified(datemodified);
                sale.setDeliveryDate(deliverydate);
                sale.setTransaction(transaction);
                sale.setBlocked(blocked);
                sale.setDueDate(duedate);
                sale.setTerms(terms);
                sale.setPrintX(printx);
                sale.setPrintlayout(printlayout);
                sale.setAudit(audit);
                sale.setPaymentMethod(paymentmethod);
                sale.setDateDeposit(datedeposit);
                sale.setCheckDate(checkdate);
                sale.setNotification(notification);
                sale.setItemCount(itemcount);
                sales.add(sale);
            }
            return new PagedResult<>(sales, sales.size());
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }
}
