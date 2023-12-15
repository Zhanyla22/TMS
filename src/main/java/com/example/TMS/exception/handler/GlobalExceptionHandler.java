package com.example.TMS.exception.handler;

import com.example.TMS.dto.ErrorResponseDto;
import com.example.TMS.exception.BaseException;
import com.example.TMS.exception.common.*;
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
    public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTaskNotFoundException(TaskNotFoundException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = NotAllowedException.class)
    public ResponseEntity<ErrorResponseDto> handleNotAllowedException(NotAllowedException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = CommentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCommentNotFoundException(CommentNotFoundException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }


    //TODO:check

    /**
     * обработка исключений RuntimeException
     *
     * @param e
     * @return HTTP ответ со статусом 500
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }
}
