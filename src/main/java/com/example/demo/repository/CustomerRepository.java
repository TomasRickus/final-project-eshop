package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByUsername(String username);

    Optional<Customer> findCustomerById(Long id);

    List<Customer> findByUsernameStartingWithIgnoreCase(String prefix);
}
