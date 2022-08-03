package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;

    public OrdersController(OrdersRepository ordersRepository, OrdersService ordersService, OrdersRepository ordersRepository1) {
        this.ordersService = ordersService;
        this.ordersRepository = ordersRepository1;
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ordersService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<List<Orders>> addOrder(@RequestBody Orders orders) {
        return ordersService.save(orders);
    }

    @GetMapping("/byid/{id}")
    public Optional<Orders> getProductById(@PathVariable(name = "id") Long id) {
        return ordersService.findOrdersById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<List<Orders>> updateOrder(@RequestBody Orders order) {
        return ordersService.update(order);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Orders>> deleteOrder(@PathVariable(name = "id") Long id) {
        return ordersService.delete(id);
    }
}
