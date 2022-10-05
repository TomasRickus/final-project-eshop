package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserRequestValidationException extends Exception {
    public UserRequestValidationException(String message) {
        super(message);
    }
}
