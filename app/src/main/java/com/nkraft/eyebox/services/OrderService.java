package com.nkraft.eyebox.services;

import com.google.gson.Gson;
import com.nkraft.eyebox.models.Order2;

import java.util.List;

public class OrderService extends RBaseService<Order2> {

    private OrderService() {
        super("orders.php");
    }

    private static OrderService _instance = new OrderService();

    public static OrderService instance() {
        return _instance;
    }

    public PagedResult<Order2> submitOrders(List<Order2> orders) {
        Gson gson = new Gson();
        String orderString = gson.toJson(orders);
        return postObject(action("submit"), makeValue("orders", orderString));
    }
}
