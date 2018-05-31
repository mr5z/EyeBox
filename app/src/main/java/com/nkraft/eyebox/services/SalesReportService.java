package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.shit.SalesReport;

import java.util.List;

public class SalesReportService extends RBaseService<SalesReport> {
    private SalesReportService() {
        super("sales_report.php");
    }

    private static SalesReportService _instance;

    public static SalesReportService instance() {
        if (_instance == null) {
            _instance = new SalesReportService();
        }
        return _instance;
    }

    public PagedResult<List<SalesReport>> getAllSalesReport() {
        return getList(action("get"));
    }

}
