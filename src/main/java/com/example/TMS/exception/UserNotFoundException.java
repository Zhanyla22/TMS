package com.example.TMS.exception;

import com.example.TMS.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
