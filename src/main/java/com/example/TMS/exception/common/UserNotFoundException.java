package com.example.TMS.exception.common;

import com.example.TMS.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(Long userId, HttpStatus httpStatus) {
        super("User not found by id - %d".formatted(userId), httpStatus);
    }

    public UserNotFoundException(String email, HttpStatus httpStatus) {
        super("User not found by email - %s".formatted(email), httpStatus);
    }
}
