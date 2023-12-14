package com.example.TMS.exception.common;

import com.example.TMS.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseException {

    public AuthenticationException(HttpStatus status) {
        super("Invalid login or password", status);
    }
}
