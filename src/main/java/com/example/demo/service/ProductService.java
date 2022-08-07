package com.example.demo.service;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    public static final String SAVING_PRODUCT = "Saving product ";
    public static final String FOUND_PRODUCT = "Found product/s ";
    public static final String TO_JSON = "Generating Json file.";
    public static final String NOT_GENERATE_JSON_FILE = "Could not generate json file.";
    public static final String JSON = "products.json";
    public static final String PRODUCT_BY_ID = "Product by id ";
    public static final String NOT_FOUND = " was not found";
    public static final String NOT_CREATED = "Product not created!";
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final ProductPdfService productPdfService;

    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper, ProductPdfService productPdfService) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
        this.productPdfService = productPdfService;
    }

    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        LOGGER.info(FOUND_PRODUCT);
        convertToJson(products);
        productPdfService.exportProductsToPdf(products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> save(final Product product) {
        if(product != null) {
            productRepository.save(product);
            LOGGER.info(SAVING_PRODUCT + product.getTitle());
            final List<Product> allProducts = productRepository.findAll();
            return new ResponseEntity<>(allProducts, HttpStatus.CREATED);
        }else {
            LOGGER.error(NOT_CREATED);
        }
        return null;
    }

    public ResponseEntity<List<Product>> findByTitle(final String title) {
        List<Product> products = productRepository.findByTitle(title);
        LOGGER.info(FOUND_PRODUCT);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> findByTitleStartingWith(final String prefix) {
        List<Product> products = productRepository.findByTitleStartingWithIgnoreCase(prefix);
        LOGGER.info(FOUND_PRODUCT);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<Product> findProductById(Long id) {
        final Product findById = productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_BY_ID + id + NOT_FOUND));
        LOGGER.info(FOUND_PRODUCT);
        return new ResponseEntity<>(findById, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> update(final Product product) {
        productRepository.save(product);
        final List<Product> allProducts = productRepository.findAll();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    public void convertToJson(final List<Product> products) {
        try {
            LOGGER.info(TO_JSON);
            objectMapper.writeValue(new File(JSON), products);
        } catch (IOException e) {
            LOGGER.error(NOT_GENERATE_JSON_FILE);
            e.printStackTrace();
        }
    }

    public ResponseEntity<List<Product>> delete(final Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
