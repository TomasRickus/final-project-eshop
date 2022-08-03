package com.example.demo.service;

import com.example.demo.exception.CustomerRequestValidationException;
import com.example.demo.model.Customer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerRequestValidationService {

    public static final String USERNAME = "Username ";
    public static final String EMAIL = "Email ";
    public static final String PASSWORD = "Password ";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestValidationService.class);
    public static final String MISSING_FIELDS = "Customer request can not be submitted due to missing fields ";
    public static final String SUCCESSFULLY = "Customer request validation successfully.";

    public void validateRequest(Customer customer) throws CustomerRequestValidationException {
        List<String> missingFields = new ArrayList<>();

        if (StringUtils.isEmpty(customer.getUsername())) {
            missingFields.add(USERNAME);
        }
        if (null == (customer.getEmail())) {
            missingFields.add(EMAIL);
        }
        if (null == (customer.getPassword())) {
            missingFields.add(PASSWORD);
        }

        if (!missingFields.isEmpty()) {
            throw new CustomerRequestValidationException(MISSING_FIELDS + missingFields);
        }
        LOGGER.info(SUCCESSFULLY);
    }
}

