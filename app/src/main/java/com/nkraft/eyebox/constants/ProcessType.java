package com.nkraft.eyebox.constants;

public enum ProcessType {
    UNDEFINED,
    TRANSACTIONS,
    PAYMENTS,
    PRODUCTS,
    CLIENTS,
    ORDERS,
    VISITS;

    public final int flag;

    ProcessType() {
        flag = 1 << this.ordinal();
    }
}