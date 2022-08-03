package com.example.demo.controller;


import com.example.demo.exception.CustomerRequestValidationException;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerRequestValidationService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final OrdersService ordersService;
    private final CustomerRequestValidationService customerRequestValidationService;

    public CustomerController(CustomerService customerService, OrdersService ordersService, CustomerRequestValidationService customerRequestValidationService) {
        this.customerService = customerService;
        this.ordersService = ordersService;
        this.customerRequestValidationService = customerRequestValidationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/findbyusername/{username}")
    public ResponseEntity<List<Customer>> findByUsername(@PathVariable String username) {
        return customerService.findByUsername(username);
    }

    @GetMapping("/findbyusernamestart/{prefix}")
    public ResponseEntity<List<Customer>> findByUsernameStarts(@PathVariable String prefix) {
        return customerService.findByUsernameStartingWith(prefix);
    }

    @PostMapping("/add")
    public ResponseEntity<List<Customer>> addCustomer(@RequestBody Customer customer) throws CustomerRequestValidationException {
        customerRequestValidationService.validateRequest(customer);
        return customerService.save(customer);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Long id) {
        return customerService.findCustomerById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<List<Customer>> updateCustomer(@RequestBody Customer customer) throws CustomerRequestValidationException {
        customerRequestValidationService.validateRequest(customer);
        return customerService.update(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Customer>> deleteCustomer(@PathVariable(name = "id") Long id) {
        return customerService.delete(id);
    }

    @PostMapping("/addtoorder")
    public ResponseEntity<List<Customer>> addProductToOrder(@RequestBody Customer customer) {
        ordersService.orderExistValidateForCustomer(customer);
        return customerService.save(customer);
    }
}
