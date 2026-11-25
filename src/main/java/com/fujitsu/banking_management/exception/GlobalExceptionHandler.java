
package com.fujitsu.banking_management.exception;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        Map<String, Object> error = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        Map<String, Object> error = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            validationErrors.put(
                error.getField(),
                error.getDefaultMessage()
            )
        );

        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "messages", validationErrors,
                "path", request.getRequestURI()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }
}
