package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerServices customerServices;

    // GET /customers/orders
    // Returns all customers with their orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomerOrders() {
        List<Customer> list = customerServices.findAllCustomerOrders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET /customers/customer/{id}
    // Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customer/{customerID}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByID(@PathVariable long customerID) {
        Customer c = customerServices.findCustomerByID(customerID);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    // GET /customers/namelike/{likename}
    // Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/namelike/{keyword}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByKeyword(@PathVariable String keyword) {
        List<Customer> list = customerServices.findCustomerByKeyword(keyword);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET /customers/orders/count
    // Return a list of all customers with the number of orders they have placed.
    @GetMapping(value = "/orders/count", produces = {"application/json"})
    public ResponseEntity<?> findOrderCount() {
        List<OrderCount> list = customerServices.findOrderCount();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // POST /customers/customer
    // Adds a new customer including any new orders
    @PostMapping(value = "/customer", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) {
        // Resets Custcode to 0 to ensure create new customer
        newCustomer.setCustcode(0);

        // Sends incoming values to customerServices.save method
        newCustomer = customerServices.save(newCustomer);

        // Generates response header with new URL pointing to newly created record
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customerID}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        // Returns null body, header and response status CREATED
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT /customers/customer/{custcode}
    // Replaces customer record including associated orders with data provided

    // PATCH /customers/customer/{custcode}
    // Uupdates customers with new data. Only new data is to be sent from the frontend client.

    // DELETE /customers/customer/{custcode}
    // Deletes the given customer including any associated orders

}
