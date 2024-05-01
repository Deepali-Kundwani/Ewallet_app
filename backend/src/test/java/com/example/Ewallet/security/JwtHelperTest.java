package com.example.Ewallet.security;

import com.example.Ewallet.collections.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class JwtHelperTest {

    @Mock
    private JwtHelper jwtHelper;

    @Test
    void getUsernameFromToken() {
        User user = new User();
        user.setName("Gaurav");
        user.setPassword("1234");
        String token = jwtHelper.generateToken(user);
        String username = jwtHelper.getUsernameFromToken(token);
        verify(jwtHelper,times(1)).getUsernameFromToken(token);
    }

    @Test
    void getExpirationDateFromToken() {
        User user = new User();
        user.setName("Gaurav");
        user.setPassword("1234");
        String token = jwtHelper.generateToken(user);

        Date expirationDate = jwtHelper.getExpirationDateFromToken(token);
        verify(jwtHelper, times(1)).getExpirationDateFromToken(token);
    }

    @Test
    void generateToken() {
        User user = new User();
        user.setName("Gaurav");
        user.setPassword("1234");
        String token = jwtHelper.generateToken(user);
        verify(jwtHelper, times(1)).generateToken(user);
    }
}