package com.example.Ewallet.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CashbackTest {

    @Test
    void getId() {
        Cashback cashback = new Cashback();
        cashback.setId("1234");
        assertEquals("1234", cashback.getId());
    }

    @Test
    void getUserId() {
        Cashback cashback = new Cashback();
        cashback.setUserId("5678");
        assertEquals("5678", cashback.getUserId());
    }

    @Test
    void getAmount() {
        Cashback cashback = new Cashback();
        cashback.setAmount(123.0);
        assertEquals(123.0, cashback.getAmount());
    }

    @Test
    void getDescription() {
        Cashback cashback = new Cashback();
        cashback.setDescription("Received from Ewallet");
        assertEquals("Received from Ewallet", cashback.getDescription());
    }

    @Test
    void getDate() {
        Cashback cashback = new Cashback();
        LocalDate date = LocalDate.now();
        cashback.setDate(date);
        assertEquals(date, cashback.getDate());
    }
}