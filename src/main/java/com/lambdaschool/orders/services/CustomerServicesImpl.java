package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentRepository;
import com.lambdaschool.orders.repositories.CustomerRepository;
//import com.lambdaschool.orders.views.CustomerAdvanceAmt;
import com.lambdaschool.orders.repositories.PaymentRepository;
import com.lambdaschool.orders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    CustomerRepository custrepos;

    @Autowired
    AgentRepository agentrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public List<Customer> findAllCustomerOrders() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerByID(long customerID) {
        return custrepos.findById(customerID)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customerID + " was not found."));
    }

    @Override
    public List<Customer> findCustomerByKeyword(String keyword) {

        List<Customer> list = custrepos.findByCustnameContainingIgnoringCase(keyword);
        return list;
    }

    @Override
    public List<OrderCount> findOrderCount() {
        List<OrderCount> list = custrepos.findOrderCount();
        return list;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {

        // Create a  newCustomer object
        Customer newCustomer = new Customer();

        // If the incoming custcode != 0, find the existing customer and assign that ID to newCustomer (used with PUT
        // & PATCH requests)
        // If the incoming custcode == 0, it's new customer, so do nothing. (used with POST requests)
        if(customer.getCustcode() != 0) {
            findCustomerByID(customer.getCustcode());
            newCustomer.setCustcode(customer.getCustcode());
        }

        // Set the incoming customer values into their fields in newCustomer
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        // Assign agent - must already exist or will throw an error
        newCustomer.setAgent(agentrepos.findById(customer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " was not " +
                        "found."))
        );

        // Add orders
        // Clear any existing orders in newCustomer first
        newCustomer.getOrders().clear();

        // Iterate through order list and assign amounts to their fields in newCustomer
        for (Order o : customer.getOrders()) {
            // Create newOrder object from incoming values -- must follow Order constructor
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrderdescription());

            // Clear any existing payments in newOrder first
            newOrder.getPayments().clear();

            // Iterate through payment list and assign amounts to their fields in newOrder
            for (Payment p : o.getPayments()) {
                // Create newPayment object -- value must already exist in DB or throw error
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment ID " + p.getPaymentid() + " was not " +
                                "found."));
                // Add newPayment object to newOrder
                newOrder.getPayments().add(newPayment);
            }
            // Add newOrder object to newCustomer
            newCustomer.getOrders().add(newOrder);
        }

        // Save newCustomer to the DB
        return custrepos.save(newCustomer);
    }
}