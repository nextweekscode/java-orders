package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
//import com.lambdaschool.orders.views.CustomerAdvanceAmt;

import java.util.List;

public interface OrderServices {

    Order findOrderByID(long orderID);

    List<Order> getCustAdvanceAmt(double amount);

    Order save(Order order);
}
