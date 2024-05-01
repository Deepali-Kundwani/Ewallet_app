package com.example.Ewallet.kafka.services;

import com.example.Ewallet.collections.PendingUser;
import com.example.Ewallet.collections.User;
import com.example.Ewallet.kafka.dto.EmailVerificationMessage;
import com.example.Ewallet.kafka.dto.GeneralMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailConsumerService {
    @Autowired
    private JavaMailSender mailSender;
    @KafkaListener(topics = "email-verification-topic", groupId = "group-1")
    public void consumeEmailVerificationMessage(EmailVerificationMessage emailVerificationMessage) throws MessagingException, UnsupportedEncodingException {
        PendingUser user = emailVerificationMessage.getPendingUser();
        String siteURL = emailVerificationMessage.getSiteURL1();
        String subject = "Please verify your registration";
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>Please click the link below to verify your registration. You have 1 hour to verify your account after which this link will expire.</p>";
        String verifyURL = siteURL + "/auth/verify?code=" + user.getToken();
        mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @KafkaListener(topics = "email-credit-topic", groupId = "group-1")
    public void consumeCreditEmailMessage(GeneralMessage generalMessage) throws MessagingException, UnsupportedEncodingException{
        Double value = generalMessage.getValue();
        User receiver = generalMessage.getReceiver();
        User sender = generalMessage.getSender();
        String subject = "Account credited with Rs. " + value;
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + receiver.getName() + ",</p>";
        mailContent += "<p>Your account is credited with Rs. " + value + " From " + sender.getName() + "</p>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(receiver.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @KafkaListener(topics = "email-debit-topic", groupId = "group-1")
    public void consumeDebitEmailMessage(GeneralMessage generalMessage) throws MessagingException, UnsupportedEncodingException{
        Double value = generalMessage.getValue();
        User receiver = generalMessage.getReceiver();
        User sender = generalMessage.getSender();
        String subject = "Account debited with Rs. " + value;
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + sender.getName() + ",</p>";
        mailContent += "<p>Your account is debited with Rs. " + value + " to " + receiver.getName() + "</p>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(sender.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @KafkaListener(topics = "email-recharge-topic", groupId = "group-1")
    public void consumeRechargeEmailMessage(GeneralMessage generalMessage) throws MessagingException, UnsupportedEncodingException{
        User user = generalMessage.getSender();
        Double value = generalMessage.getValue();
        String subject = "Recharge of Rs. " + value;
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>Recharge of Rs. " + value + " has done in your Ewallet account</p>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @KafkaListener(topics = "email-cashback-topic", groupId = "group-2")
    public void consumeCashbackEmailMessage(GeneralMessage generalMessage) throws MessagingException, UnsupportedEncodingException{
        User user = generalMessage.getSender();
        Double value = generalMessage.getValue();
        String subject = "Received cashback of Rs. " + value;
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>You got a cashback of Rs. " + value + " in your Ewallet account</p>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }
    @KafkaListener(topics = "email-withdraw-topic", groupId = "group-1")
    public void consumeWithdrawEmailMessage(GeneralMessage generalMessage) throws MessagingException, UnsupportedEncodingException{
        User user = generalMessage.getSender();
        Double value = generalMessage.getValue();
        String subject = "Withdraw of Rs. " + value;
        String senderName = "Ewallet team";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>Withdraw of Rs. " + value + " has done from your Ewallet account</p>";
        mailContent += "<p>Thank You<br>The Ewallet Team</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("walletbanker027@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

}
