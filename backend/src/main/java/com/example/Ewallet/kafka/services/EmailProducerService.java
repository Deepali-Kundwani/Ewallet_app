package com.example.Ewallet.kafka.services;

import com.example.Ewallet.collections.PendingUser;
import com.example.Ewallet.collections.User;
import com.example.Ewallet.kafka.dto.EmailVerificationMessage;
import com.example.Ewallet.kafka.dto.GeneralMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEmailVerificationMessage(PendingUser pendingUser, String siteURL1){
        EmailVerificationMessage message = new EmailVerificationMessage(pendingUser, siteURL1);
        kafkaTemplate.send("email-verification-topic", message);
    }

    public void sendCreditEmailMessage(Double value, User receiver, User sender){
        GeneralMessage message = new GeneralMessage(value, sender, receiver);
        kafkaTemplate.send("email-credit-topic", message);
    }

    public void sendDebitEmailMessage(Double value, User sender, User receiver){
        GeneralMessage message = new GeneralMessage(value, sender, receiver);
        kafkaTemplate.send("email-debit-topic", message);
    }

    public void sendRechargeEmailMessage(Double value, User user){
        GeneralMessage message = new GeneralMessage();
        message.setSender(user);
        message.setValue(value);
        kafkaTemplate.send("email-recharge-topic", message);
    }

    public void sendCashbackEmailMessage(Double value, User user){
        GeneralMessage message = new GeneralMessage();
        message.setSender(user);
        message.setValue(value);
        kafkaTemplate.send("email-cashback-topic", message);
    }

    public void sendWithdrawEmailMessage(Double value, User user){
        GeneralMessage message = new GeneralMessage();
        message.setSender(user);
        message.setValue(value);
        kafkaTemplate.send("email-withdraw-topic", message);
    }
}
