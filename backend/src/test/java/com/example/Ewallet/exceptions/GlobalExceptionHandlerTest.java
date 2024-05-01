package com.example.Ewallet.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleAmountNotAvailableException() {
        AmountNotAvailableException exception = new AmountNotAvailableException("Amount not available");
        ResponseEntity<String> response = globalExceptionHandler.handleItemNotFoundException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Amount not available", response.getBody());
    }

    @Test
    public void testHandleUserAlreadyExistException() {
        UserAlreadyExistException exception = new UserAlreadyExistException("User already exists");
        ResponseEntity<String> response = globalExceptionHandler.handleUserAlreadyExistException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    public void testHandleBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Bad credentials");
        ResponseEntity<String> response = globalExceptionHandler.handleBadCredentialsException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad credentials", response.getBody());
    }
}
