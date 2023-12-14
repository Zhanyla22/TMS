package com.example.TMS.exception.common;

import com.example.TMS.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowedException extends BaseException {
    public NotAllowedException(String action,HttpStatus httpStatus) {
        super("Not allowed for - %s".formatted(action), httpStatus);
    }
}
