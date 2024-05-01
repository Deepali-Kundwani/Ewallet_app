package com.example.Ewallet.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionTest {

    @Test
    void getId() {
        Transaction transaction = new Transaction();
        transaction.setId("123");
        assertEquals("123", transaction.getId());
    }

    @Test
    void getUserId() {
        Transaction transaction = new Transaction();
        transaction.setUserId("456");
        assertEquals("456", transaction.getUserId());
    }

    @Test
    void getType() {
        Transaction transaction = new Transaction();
        transaction.setType("Cashback");
        assertEquals("Cashback", transaction.getType());
    }

    @Test
    void getName() {
        Transaction transaction = new Transaction();
        transaction.setName("Gaurav");
        assertEquals("Gaurav", transaction.getName());
    }

    @Test
    void getDescription() {
        Transaction transaction = new Transaction();
        transaction.setDescription("Received from Gaurav");
        assertEquals("Received from Gaurav", transaction.getDescription());
    }

    @Test
    void getAmount() {
        Transaction transaction = new Transaction();
        transaction.setAmount(123.0);
        assertEquals(123.0, transaction.getAmount());
    }

    @Test
    void getDate() {
        Transaction transaction = new Transaction();
        LocalDate date = LocalDate.now();
        transaction.setDate(date);
        assertEquals(date, transaction.getDate());
    }
}