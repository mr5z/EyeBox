package com.nkraft.eyebox.constants;

public enum ProcessType {
    UNDEFINED,
    TRANSACTIONS,
    SUBMIT_PAYMENTS,
    PRODUCTS,
    CLIENTS,
    SALES,
    ORDERS,
    VISITS,
    TERMS,
    BANKS,
    SUBMIT_ORDERS,
    CREDITS,
    SALES_REPORT;

    public final int flag;

    ProcessType() {
        flag = 1 << this.ordinal();
    }

    public static boolean hasFlag(int flags, ProcessType processType) {
        return (flags & processType.flag) != 0;
    }
}