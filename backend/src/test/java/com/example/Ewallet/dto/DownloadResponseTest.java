package com.example.Ewallet.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DownloadResponseTest {

    @Test
    void testGetTid() {
        DownloadResponse downloadResponse = new DownloadResponse();
        downloadResponse.setTid("12345");
        assertEquals("12345", downloadResponse.getTid());
    }

    @Test
    void testGetAmount() {
        DownloadResponse downloadResponse = new DownloadResponse();
        downloadResponse.setAmount(100.0);
        assertEquals(100.0, downloadResponse.getAmount(), 0.01);
    }

    @Test
    void testGetDescription() {
        DownloadResponse downloadResponse = new DownloadResponse();
        downloadResponse.setDescription("Test Description");
        assertEquals("Test Description", downloadResponse.getDescription());
    }

    @Test
    void testGetDate() {
        DownloadResponse downloadResponse = new DownloadResponse();
        LocalDate date = LocalDate.of(2023, 10, 17);
        downloadResponse.setDate(date);
        assertEquals(date, downloadResponse.getDate());
    }

    @Test
    void testGetType() {
        DownloadResponse downloadResponse = new DownloadResponse();
        downloadResponse.setType("Test Type");
        assertEquals("Test Type", downloadResponse.getType());
    }
}