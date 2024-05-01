package com.example.Ewallet.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferRequestTest {

    @Test
    void getEmail() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setEmail("gaurav@gmail.com");
        assertEquals("gaurav@gmail.com", transferRequest.getEmail());
    }

    @Test
    void getAmount() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount(123.0);
        assertEquals(123.0, transferRequest.getAmount());
    }
}