package com.example.TMS.exception;

import com.example.TMS.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class NotAllowedException extends BaseException {
    public NotAllowedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
