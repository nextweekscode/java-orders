package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
//import com.lambdaschool.orders.views.CustomerAdvanceAmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderServices orderServices;

    // GET /orders/order/{id}
    // Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{orderID}", produces = {"application/json"})
    public ResponseEntity<?> findOrderByID(@PathVariable long orderID) {
        Order o = orderServices.findOrderByID(orderID);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    // GET /orders/advanceamount
    // Returns all orders with their customers that have an advanceamount greater than 0.
    @GetMapping(value = "/advanceamount/{amount}", produces = {"application/json"})
    public ResponseEntity<?> getCustAdvanceAmt(@PathVariable double amount) {
        List<Order> list = orderServices.getCustAdvanceAmt(amount);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/advanceamount", produces = {"application/json"})
    public ResponseEntity<?> getCustAdvanceAmt() {
        List<Order> list = orderServices.getCustAdvanceAmt(0);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
