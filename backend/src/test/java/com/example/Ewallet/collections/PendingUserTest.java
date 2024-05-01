package com.example.Ewallet.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PendingUserTest {

    @Test
    void getId() {
        PendingUser pendingUser = new PendingUser();
        pendingUser.setId("1234");
        assertEquals("1234", pendingUser.getId());
    }

    @Test
    void getName() {
        PendingUser pendingUser = new PendingUser();
        pendingUser.setName("Gaurav");
        assertEquals("Gaurav", pendingUser.getName());
    }

    @Test
    void getEmail() {
        PendingUser pendingUser = new PendingUser();
        pendingUser.setEmail("gaurav@gmail.com");
        assertEquals("gaurav@gmail.com", pendingUser.getEmail());
    }

    @Test
    void getPassword() {
        PendingUser pendingUser = new PendingUser();
        pendingUser.setPassword("5678");
        assertEquals("5678", pendingUser.getPassword());
    }

    @Test
    void getToken() {
        PendingUser pendingUser = new PendingUser();
        pendingUser.setToken("987");
        assertEquals("987",pendingUser.getToken());
    }

    @Test
    void getExpirationDate() {
        PendingUser pendingUser = new PendingUser();
        Date date = new Date();
        pendingUser.setExpirationDate(date);
        assertEquals(date, pendingUser.getExpirationDate());
    }
}