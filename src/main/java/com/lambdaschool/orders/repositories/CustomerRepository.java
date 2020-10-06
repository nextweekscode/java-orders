package com.lambdaschool.orders.repositories;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.OrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByCustnameContainingIgnoringCase(String keyword);

    @Query(value = "SELECT c.custname name, count(o.ordnum) ordercount " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY name " +
            "ORDER BY ordercount DESC, name", nativeQuery = true)
    List<OrderCount> findOrderCount();
}