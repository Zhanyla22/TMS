package com.example.TMS.exception.common;

import com.example.TMS.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends BaseException {

    public TaskNotFoundException(UUID uuid, HttpStatus httpStatus) {
        super("Task not found by UUID - %d".formatted(uuid), httpStatus);
    }
}
