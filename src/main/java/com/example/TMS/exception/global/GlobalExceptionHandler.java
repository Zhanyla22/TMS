package com.example.TMS.exception.global;

import com.example.TMS.dto.ResponseDto;
import com.example.TMS.enums.ResultCode;
import com.example.TMS.exception.NotAllowedException;
import com.example.TMS.exception.TaskNotFoundException;
import com.example.TMS.exception.UserNotFoundException;
import com.example.TMS.exception.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * обработка исключений BaseException
     *
     * @param e
     * @return сообщение об ошибке и HTTP статус
     */
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = NotAllowedException.class)
    public ResponseEntity<Object> handleNotAllowedException(NotAllowedException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    /**
     * обработка исключений RuntimeException
     *
     * @param e
     * @return HTTP ответ со статусом 500
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return buildBaseResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * структурированный HTTP-ответ на основе параметров
     *
     * @param message - сообщение об ошибке/исключение
     * @param status  - http статус
     * @return структурированный HTTP ответ
     */
    private ResponseEntity<Object> buildBaseResponseMessage(String message, HttpStatus status) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(ResultCode.EXCEPTION)
                        .message(message)
                        .build(),
                status
        );
    }
}
