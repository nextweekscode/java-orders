package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ordnum;

    private double ordamount;
    private double advanceamount;
    private String orderdescription;

    @ManyToOne
    @JoinColumn(name = "custcode", nullable = false)
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "orderspayments",
            joinColumns = @JoinColumn(name = "ordnum"),
            inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    public Order() {
        // Default constructor required by JPA
    }

    public Order(double ordamount,
                 double advanceamount,
                 Customer customer,
                 String orderdescription) {
        this.ordamount = ordamount;
        this.advanceamount = advanceamount;
        this.customer = customer;
        this.orderdescription = orderdescription;
    }

    public long getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(long ordernum) {
        this.ordnum = ordernum;
    }

    public double getOrdamount() {
        return ordamount;
    }

    public void setOrdamount(double orderamount) {
        this.ordamount = orderamount;
    }

    public double getAdvanceamount() {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount) {
        this.advanceamount = advanceamount;
    }

    public String getOrderdescription() {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription) {
        this.orderdescription = orderdescription;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayments(Payment payment) {
        this.getPayments().add(payment);
    }
}
© 2020 GitHub, Inc.
        Terms
        Privacy
        Security
        Status
        Help
        Contact GitHub
        Pricing
        API
        Training
        Blog
        About

