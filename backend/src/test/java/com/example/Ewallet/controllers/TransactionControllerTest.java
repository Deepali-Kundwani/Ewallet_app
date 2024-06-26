package com.example.Ewallet.controllers;

import com.example.Ewallet.collections.Cashback;
import com.example.Ewallet.collections.Transaction;
import com.example.Ewallet.collections.User;
import com.example.Ewallet.dto.DownloadResponse;
import com.example.Ewallet.dto.TransferRequest;
import com.example.Ewallet.exceptions.AmountNotAvailableException;
import com.example.Ewallet.security.JwtAuthenticationFilter;
import com.example.Ewallet.security.JwtHelper;
import com.example.Ewallet.services.TransactionService;
import com.example.Ewallet.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void transactionTransfer() throws MessagingException, UnsupportedEncodingException {
        String Token = "789";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + Token);
        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(Token)).thenReturn(username);

        User sender = new User();
        sender.setUserId("123");
        sender.setName("test");
        sender.setEmail("test@gmail.com");
        sender.setWalletBalance(100.0);
        User receiver = new User();
        receiver.setUserId("456");
        receiver.setName("rahul");
        receiver.setEmail("kundwanirahul22@gmail.com");
        receiver.setWalletBalance(100.0);
        when(userService.getUserByEmail(username)).thenReturn(sender);
        when(userService.getUserByEmail(receiver.getEmail())).thenReturn(receiver);
        doNothing().when(transactionService).addBalance(anyDouble(), anyString());
        doNothing().when(transactionService).decreaseBalance(anyDouble(), anyString());
        doNothing().when(transactionService).creditMail(anyDouble(), any(User.class), any(User.class));
        doNothing().when(transactionService).debitMail(anyDouble(), any(User.class), any(User.class));
        doNothing().when(transactionService).createCredit(anyDouble(), any(User.class), any(User.class));
        doNothing().when(transactionService).createDebit(anyDouble(), any(User.class), any(User.class));

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setEmail(receiver.getEmail());
        transferRequest.setAmount(50.0);
        String result = transactionController.transactionTransfer(transferRequest, request);
        assertEquals("Transfer Successfull", result);
    }

    @Test
    void recharge() throws MessagingException, UnsupportedEncodingException {
        String token = "123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("456");
        user.setName("test");
        user.setEmail("test@gmail.com");
        user.setWalletBalance(100.0);

        Transaction transaction = new Transaction();
        transaction.setId("123");
        transaction.setAmount(123.0);
        transaction.setName("Gaurav");

        when(userService.getUserByEmail(username)).thenReturn(user);
        when(transactionService.rechargeWallet(anyDouble(), any(User.class))).thenReturn(transaction);
        doNothing().when(transactionService).rechargeMail(anyDouble(), any(User.class));
        doNothing().when(transactionService).createCashback(anyDouble(), any(User.class), any(Transaction.class));
        doNothing().when(transactionService).cashbackMail(anyDouble(), any(User.class));

        Double rechargeValue = 100.0;
        String result = transactionController.recharge(rechargeValue, request);
        assertEquals("Recharge Successfull", result);
    }

    @Test
    void withdraw() throws MessagingException, UnsupportedEncodingException {
        String token = "123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("456");
        user.setName("test");
        user.setEmail("test@gmail.com");
        user.setWalletBalance(100.0);

        when(userService.getUserByEmail(username)).thenReturn(user);
        doNothing().when(transactionService).withdrawWallet(anyDouble(), any(User.class));
        doNothing().when(transactionService).withdrawMail(anyDouble(), any(User.class));

        Double withdrawValue = 50.0;
        String result = transactionController.withdraw(withdrawValue, request);
        assertEquals("Withdraw Successfull", result);
    }

    @Test
    void withdrawInsufficientBalanceTest() throws MessagingException, UnsupportedEncodingException{
        String token = "123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("456");
        user.setName("test");
        user.setEmail("test@gmail.com");
        user.setWalletBalance(50.0);

        when(userService.getUserByEmail(username)).thenReturn(user);
        doNothing().when(transactionService).withdrawWallet(anyDouble(), any(User.class));
        doNothing().when(transactionService).withdrawMail(anyDouble(), any(User.class));

        AmountNotAvailableException exception = assertThrows(AmountNotAvailableException.class, () -> {
            transactionController.withdraw(100.0, request);
        });
        assertEquals("Amount not available", exception.getMessage());
    }

    @Test
    void transactionList() {
        String token = "1234";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("123");
        user.setName("test");
        user.setEmail("test@gmail.com");
        when(userService.getUserByEmail(username)).thenReturn(user);

        List<Transaction> mockTransactions = new ArrayList<>();
        when(transactionService.transactions(user.getUserId())).thenReturn(mockTransactions);
        List<Transaction> result = transactionController.transactionList(request);

        assertNotNull(result);
        assertEquals(mockTransactions, result);
    }

    @Test
    void downloadList() {
        String token = "123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@gmail.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("456");
        user.setName("test");
        user.setEmail("test@gmail.com");
        when(userService.getUserByEmail(username)).thenReturn(user);

        List<DownloadResponse> mockDownloadRequests = new ArrayList<>();
        when(transactionService.downloads(user.getUserId())).thenReturn(mockDownloadRequests);

        List<DownloadResponse> result = transactionController.downloadList(request);

        assertNotNull(result);
        assertEquals(mockDownloadRequests, result);
    }

    @Test
    void cashbackList() {
        String token = "123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String username = "test@example.com";
        when(jwtHelper.getUsernameFromToken(token)).thenReturn(username);

        User user = new User();
        user.setUserId("456");
        user.setName("test");
        user.setEmail("test@example.com");
        when(userService.getUserByEmail(username)).thenReturn(user);

        List<Cashback> mockCashbacks = new ArrayList<>();
        when(transactionService.cashbacks(user.getUserId())).thenReturn(mockCashbacks);

        List<Cashback> result = transactionController.cashbackList(request);

        assertNotNull(result);
        assertEquals(mockCashbacks, result);
    }
}