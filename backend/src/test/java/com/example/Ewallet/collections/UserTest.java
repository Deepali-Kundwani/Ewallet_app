package com.example.Ewallet.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Test
    void getUserId() {
        User user = new User();
        user.setUserId("12");
        assertEquals("12", user.getUserId());
    }

    @Test
    void getName() {
        User user = new User();
        user.setName("Gaurav");
        assertEquals("Gaurav", user.getName());
    }

    @Test
    void getEmail() {
        User user = new User();
        user.setEmail("gaurav@gmail.com");
        assertEquals("gaurav@gmail.com", user.getEmail());
    }

    @Test
    void getPassword() {
        User user = new User();
        user.setPassword("345");
        assertEquals("345", user.getPassword());
    }

    @Test
    void getWalletBalance() {
        User user = new User();
        user.setWalletBalance(123.0);
        assertEquals(123.0, user.getWalletBalance());
    }

    @Test
    void getIncome() {
        User user = new User();
        user.setIncome(120.0);
        assertEquals(120.0, user.getIncome());
    }

    @Test
    void getExpenses() {
        User user = new User();
        user.setExpenses(120.0);
        assertEquals(120.0, user.getExpenses());
    }

    @Test
    void getCashback(){
        User user = new User();
        user.setCashback(120.0);
        assertEquals(120.0, user.getCashback());
    }

    @Test
    void getRegistrationDate(){
        User user = new User();
        Date date = new Date();
        user.setRegistrationDate(date);
        assertEquals(date, user.getRegistrationDate());
    }
}