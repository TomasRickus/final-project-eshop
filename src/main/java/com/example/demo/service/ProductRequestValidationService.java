package com.example.demo.service;

import com.example.demo.exception.ProductRequestValidationException;
import com.example.demo.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRequestValidationService {
    public static final String TITLE = "Title";
    public static final String PRICE = "Price";
    public static final String QUANTITY = "Quantity";
    public static final String MISSING_FIELDS = "Product request can not be submitted due to missing fields ";
    public static final String SUCCESSFULLY = "Product request validation successfully.";
    public static final String FABRIC = "Fabric";
    public static final String SIZE = "Size";
    public static final String TYPE = "Type";
    private final Logger LOGGER = LoggerFactory.getLogger(ProductRequestValidationService.class);

    public void validateRequest(Product product) throws ProductRequestValidationException {
        List<String> missingFields = new ArrayList<>();

        if (StringUtils.isEmpty(product.getTitle())) {
            missingFields.add(TITLE);
        }
        if (null == (product.getPrice())) {
            missingFields.add(PRICE);
        }
        if (null == (product.getQuantity())) {
            missingFields.add(QUANTITY);
        }
        if (StringUtils.isEmpty(product.getSize())) {
            missingFields.add(SIZE);
        }
//        if (!StringUtils.isEmpty(product.getType().toString())) {
//           return;
//        }

        if (!missingFields.isEmpty()) {
            throw new ProductRequestValidationException(MISSING_FIELDS + missingFields);
        }
        LOGGER.info(SUCCESSFULLY);
    }
}

