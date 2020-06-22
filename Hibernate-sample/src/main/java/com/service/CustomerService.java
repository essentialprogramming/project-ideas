package com.service;

import com.entities.Customer;
import com.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void addOrder(Customer customer) {

        customer.setOrders(customer.getOrders());
        customer.getOrders().forEach(order -> {

            order.setCustomer(customer);
            order.getItems().forEach(item -> {
                order.addItem(item);
                item.addToOrder(order);
            });
        });

        customerRepository.save(customer);
    }

}
