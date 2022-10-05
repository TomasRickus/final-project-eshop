package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final String FOUND_CUSTOMER = "Found customer/s ";
    public static final String SAVING_CUSTOMER = "Saving customer ";
    public static final String CUSTOMER_BY_ID = "Customer by id ";
    public static final String NOT_FOUND = " was not found";
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> save(final User user) {
        userRepository.save(user);
        LOGGER.info(SAVING_CUSTOMER + user.getUsername());
        final List<User> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    public ResponseEntity<Optional<User>> findByUsername(final String username) {
        Optional<User> user = userRepository.findByUsername(username);
        LOGGER.info(FOUND_CUSTOMER + username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<Optional<User>> findByUsernameStartingWith(final String prefix) {
        Optional<User> users = userRepository.findByUsernameStartingWithIgnoreCase(prefix);
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<User> findCustomerById(Long id) {
        final User findById = userRepository.findCustomerById(id)
                .orElseThrow(() -> new UserNotFoundException(CUSTOMER_BY_ID + id + NOT_FOUND));
        LOGGER.info(FOUND_CUSTOMER);
        return new ResponseEntity<>(findById, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> update(final User user) {
        userRepository.save(user);
        final List<User> allUser = userRepository.findAll();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> delete(final Long id) {
        userRepository.deleteById(id);
        final List<User> allUser = userRepository.findAll();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }
}
