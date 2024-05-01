package com.example.Ewallet.controllers;

import com.example.Ewallet.collections.*;
import com.example.Ewallet.dto.TransferRequest;
import com.example.Ewallet.exceptions.AmountNotAvailableException;
import com.example.Ewallet.dto.DownloadResponse;
import com.example.Ewallet.kafka.services.EmailConsumerService;
import com.example.Ewallet.kafka.services.EmailProducerService;
import com.example.Ewallet.security.JwtHelper;
import com.example.Ewallet.services.TransactionService;
import com.example.Ewallet.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private EmailProducerService emailProducerService;

    @PostMapping("/transfer")
    public String transactionTransfer(@RequestBody TransferRequest transferRequest, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = transferRequest.getEmail();
        Double value = transferRequest.getAmount();
        User receiver = userService.getUserByEmail(email);
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User sender = userService.getUserByEmail(username);
        if(sender.getEmail().equals(receiver.getEmail())){
            return "Insert receiver's email";
        }
        if(sender.getWalletBalance()<value){
            throw new AmountNotAvailableException("Amount not available in wallet");
        }
        transactionService.addBalance(value, email);
        transactionService.decreaseBalance(value, sender.getUsername());
        transactionService.createCredit(value, receiver,sender);
        transactionService.createDebit(value, sender, receiver);
        emailProducerService.sendCreditEmailMessage(value, receiver, sender);
        emailProducerService.sendDebitEmailMessage(value, sender, receiver);
        return "Transfer Successfull";
    }

    @PostMapping("/recharge")
    public String recharge(@RequestParam("value") Double value, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        Transaction transaction = transactionService.rechargeWallet(value, user);
        Double cashback = value/10;
        if(cashback>150.0){
            cashback = 150.0;
        }
        transactionService.createCashback(cashback, user, transaction);
        emailProducerService.sendRechargeEmailMessage(value, user);
        emailProducerService.sendCashbackEmailMessage(cashback, user);
        return "Recharge Successfull";
    }

    @PutMapping("/withdraw")
    public String withdraw(@RequestParam("value") Double value, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException{
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        if(user.getWalletBalance()<value){
            throw new AmountNotAvailableException("Amount not available");
        }
        transactionService.withdrawWallet(value, user);
        emailProducerService.sendWithdrawEmailMessage(value, user);
        return "Withdraw Successfull";
    }

    @GetMapping("/transactions")
    public List<Transaction> transactionList(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        return transactionService.transactions(user.getUserId());
    }

    @GetMapping("/download")
    public List<DownloadResponse> downloadList(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        return transactionService.downloads(user.getUserId());
    }

    @GetMapping("/cashback")
    public List<Cashback> cashbackList(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        return transactionService.cashbacks(user.getUserId());
    }
}
