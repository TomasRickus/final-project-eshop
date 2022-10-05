package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findCustomerById(Long id);

    Optional<User> findByUsernameStartingWithIgnoreCase(String prefix);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
