package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Sale;

import java.util.List;

public class SalesService extends RBaseService<Sale> {

    private SalesService() {
        super("sales.php");
    }

    private static SalesService _instance = new SalesService();

    public static SalesService instance() {
        return _instance;
    }

    public PagedResult<List<Sale>> getSalesByBranch(int branchNumber) {
        return getList(action("get"), makeValue("branch", branchNumber));
    }

}
