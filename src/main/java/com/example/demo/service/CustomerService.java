package com.example.demo.service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    public static final String FOUND_CUSTOMER = "Found customer/s ";
    public static final String SAVING_CUSTOMER = "Saving customer ";
    public static final String CUSTOMER_BY_ID = "Customer by id ";
    public static final String NOT_FOUND = " was not found";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerRepository.findAll();
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> save(final Customer customer) {
        customerRepository.save(customer);
        LOGGER.info(SAVING_CUSTOMER + customer.getUsername());
        final List<Customer> allCustomers = customerRepository.findAll();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> findByUsername(final String username) {
        List<Customer> customer = customerRepository.findByUsername(username);
        LOGGER.info(FOUND_CUSTOMER + username);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> findByUsernameStartingWith(final String prefix) {
        List<Customer> customers = customerRepository.findByUsernameStartingWithIgnoreCase(prefix);
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public ResponseEntity<Customer> findCustomerById(Long id) {
        final Customer findById = customerRepository.findCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_BY_ID + id + NOT_FOUND));
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(findById, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> update(final Customer customer) {
        customerRepository.save(customer);
        final List<Customer> allCustomer = customerRepository.findAll();
        return new ResponseEntity<>(allCustomer, HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> delete(final Long id) {
        customerRepository.deleteById(id);
        final List<Customer> allCustomer = customerRepository.findAll();
        return new ResponseEntity<>(allCustomer, HttpStatus.OK);
    }
}
