package com.rebels.alliance.gateways.controllers.handlers;

import com.rebels.alliance.exceptions.BusinessValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RebelControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handleBusinessValidation(BusinessValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getValidationErrors().toString());
    }
}
