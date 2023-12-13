package com.example.TMS.exception.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseException extends RuntimeException{

    HttpStatus status;

    public BaseException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
