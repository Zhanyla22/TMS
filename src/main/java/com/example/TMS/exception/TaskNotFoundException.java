package com.example.TMS.exception;

import com.example.TMS.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends BaseException {
    public TaskNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
