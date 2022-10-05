package com.example.demo.service;

import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrdersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    public static final String SAVING_ORDER = "Saving order ";
    public static final String FOUND_ORDER = "Found order/s ";
    public static final String TO_JSON = "Generating Json file.";
    public static final String NOT_GENERATE_JSON_FILE = "Could not generate json file.";
    public static final String JSON = "orders.json";
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    private final OrdersRepository ordersRepository;
    private final ObjectMapper objectMapper;
    private final Orders orders;

    public OrdersService(OrdersRepository ordersRepository, ObjectMapper objectMapper, Orders orders) {
        this.ordersRepository = ordersRepository;
        this.objectMapper = objectMapper;
        this.orders = orders;
    }


    public ResponseEntity<List<Orders>> findAll() {
        List<Orders> orders = ordersRepository.findAll();
        LOGGER.info(FOUND_ORDER);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<List<Orders>> save(final Orders order) {
        ordersRepository.save(order);
        LOGGER.info(SAVING_ORDER);
        final List<Orders> allOrders = ordersRepository.findAll();
        return new ResponseEntity<>(allOrders, HttpStatus.CREATED);
    }


    public Optional<Orders> findOrdersById(Long id) {
        final Optional<Orders> findById = ordersRepository.findById(id);
        LOGGER.info(FOUND_ORDER);
        return findById;
    }

    public ResponseEntity<List<Orders>> update(final Orders order) {
        ordersRepository.save(order);
        final List<Orders> allOrders = ordersRepository.findAll();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    public void convertToJson(final List<Orders> orders) {
        try {
            LOGGER.info(TO_JSON);
            objectMapper.writeValue(new File(JSON), orders);
        } catch (IOException e) {
            LOGGER.error(NOT_GENERATE_JSON_FILE);
            e.printStackTrace();
        }
    }

    public ResponseEntity<List<Orders>> delete(final Long id) {
        ordersRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void orderExistValidateForProduct(Product product) {
        if (product.getOrders() == null) {
            final Orders createdOrder = ordersRepository.save(new Orders());
            product.setOrders(createdOrder);
        }
    }

    public void orderExistValidateForCustomer(User user) {
        if (user.getOrders() == null) {
            final Orders createdOrder = ordersRepository.save(new Orders());
            user.setOrders(List.of(createdOrder));
        }
    }
}


