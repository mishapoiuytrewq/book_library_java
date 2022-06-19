package com.it.book_library_java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Message> handleException(NoSuchElementException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Message> handleException(BookConflictException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Message> handleException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Message> handleException(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    record Message(String message) {
    }
}
