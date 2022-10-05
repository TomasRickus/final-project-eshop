package com.example.demo.controller;


import com.example.demo.exception.UserRequestValidationException;
import com.example.demo.model.User;
import com.example.demo.service.UserRequestValidationService;
import com.example.demo.service.UserService;
import com.example.demo.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping(value = "/api/use/user")
public class UserController {

    private final UserService userService;
    private final OrdersService ordersService;
    private final UserRequestValidationService userRequestValidationService;

    public UserController(UserService userService, OrdersService ordersService, UserRequestValidationService userRequestValidationService) {
        this.userService = userService;
        this.ordersService = ordersService;
        this.userRequestValidationService = userRequestValidationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllCustomers() {
        return userService.findAll();
    }

    @GetMapping("/findbyusername/{username}")
    public ResponseEntity<Optional<User>> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/findbyusernamestart/{prefix}")
    public ResponseEntity<Optional<User>> findByUsernameStarts(@PathVariable String prefix) {
        return userService.findByUsernameStartingWith(prefix);
    }

    @PostMapping("/add")
    public ResponseEntity<List<User>> addCustomer(@RequestBody User user) throws UserRequestValidationException {
        userRequestValidationService.validateRequest(user);
        return userService.save(user);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable(name = "id") Long id) {
        return userService.findCustomerById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<List<User>> updateCustomer(@RequestBody User user) throws UserRequestValidationException {
        userRequestValidationService.validateRequest(user);
        return userService.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<User>> deleteCustomer(@PathVariable(name = "id") Long id) {
        return userService.delete(id);
    }

    @PostMapping("/addtoorder")
    public ResponseEntity<List<User>> addProductToOrder(@RequestBody User user) {
        ordersService.orderExistValidateForCustomer(user);
        return userService.save(user);
    }
    @PostMapping("/login")
    public Principal customer(Principal customer) {
        return customer;
    }
}
