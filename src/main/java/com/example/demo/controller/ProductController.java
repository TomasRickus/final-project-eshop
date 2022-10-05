package com.example.demo.controller;

import com.example.demo.exception.ProductRequestValidationException;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductRequestValidationService;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping("/api/use/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRequestValidationService productRequestValidationService;
    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;


    public ProductController(ProductService productService, ProductRequestValidationService productRequestValidationService,
                             OrdersRepository ordersRepository, OrdersService ordersService, OrdersRepository ordersRepository1) {
        this.productService = productService;
        this.productRequestValidationService = productRequestValidationService;
        this.ordersService = ordersService;
        this.ordersRepository = ordersRepository1;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.findAll();

    }

    @GetMapping("/findbytitle/{title}")
    public ResponseEntity<List<Product>> findByTitle(@PathVariable String title) {
        return productService.findByTitle(title);
    }

    @GetMapping("/findbytitlestart/{prefix}")
    public ResponseEntity<List<Product>> findByArticleStarts(@PathVariable String prefix) {
        return productService.findByTitleStartingWith(prefix);
    }

    @PostMapping("/add")
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) throws ProductRequestValidationException {
        productRequestValidationService.validateRequest(product);
        return productService.save(product);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<List<Product>> updateProduct(@RequestBody Product product) throws ProductRequestValidationException {
        productRequestValidationService.validateRequest(product);
        return productService.update(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable(name = "id") Long id) {
        return productService.delete(id);
    }

    @PostMapping("/addtoorder")
    public Orders addProductToOrder(@RequestBody Product product, Orders orders) {
        ordersService.orderExistValidateForProduct(product);
        return ordersRepository.save(orders);
    }
}