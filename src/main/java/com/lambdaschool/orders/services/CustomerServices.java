package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.OrderCount;

import java.util.List;

public interface CustomerServices {

    List<Customer> findAllCustomerOrders();

    Customer findCustomerByID(long customerID);

    List<Customer> findCustomerByKeyword(String keyword);

    List<OrderCount> findOrderCount();

    Customer save(Customer customer);
}