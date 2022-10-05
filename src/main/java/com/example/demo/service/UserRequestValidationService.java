package com.example.demo.service;

import com.example.demo.exception.UserRequestValidationException;
import com.example.demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRequestValidationService {

    public static final String USERNAME = "Username ";
    public static final String EMAIL = "Email ";
    public static final String PASSWORD = "Password ";
    public static final String CUSTOMER_WAS_NULL = "Customer was null";
    private final Logger LOGGER = LoggerFactory.getLogger(UserRequestValidationService.class);
    public static final String MISSING_FIELDS = "Customer request can not be submitted due to missing fields ";
    public static final String CUSTOMER_REQUEST_SUCCESSFULLY = "Customer request validation successfully.";

    public void validateRequest(User user) throws UserRequestValidationException {

        if (user == null) {
            throw new UserRequestValidationException(CUSTOMER_WAS_NULL);
        }
        List<String> missingFields = new ArrayList<>();

        if (StringUtils.isEmpty(user.getUsername())) {
            missingFields.add(USERNAME);
        }
        if (null == (user.getEmail())) {
            missingFields.add(EMAIL);
        }
        if (null == (user.getPassword())) {
            missingFields.add(PASSWORD);
        }

        if (!missingFields.isEmpty()) {
            throw new UserRequestValidationException(MISSING_FIELDS + missingFields);
        }
        LOGGER.info(CUSTOMER_REQUEST_SUCCESSFULLY);
    }
}

