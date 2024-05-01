package com.example.Ewallet.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginResponseTest {

    @Test
    void getJwtToken() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken("1234");
        assertEquals("1234", loginResponse.getJwtToken());
    }

    @Test
    void getUsername() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername("Gaurav");
        assertEquals("Gaurav", loginResponse.getUsername());
    }
}