package com.example.Ewallet.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginRequestTest {

    @Test
    void getEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("gaurav@gmail.com");
        assertEquals("gaurav@gmail.com", loginRequest.getEmail());
    }

    @Test
    void getPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        assertEquals("1234", loginRequest.getPassword());
    }
}