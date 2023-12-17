package com.example.TMS.exception.common;

import com.example.TMS.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException(UUID uuid, HttpStatus httpStatus) {
        super("Comment not found by UUID - %d".formatted(uuid), httpStatus);
    }
}
