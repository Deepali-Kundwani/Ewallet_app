package com.example.Ewallet.controllers;

import com.example.Ewallet.collections.PendingUser;
import com.example.Ewallet.dto.LoginRequest;
import com.example.Ewallet.dto.LoginResponse;
import com.example.Ewallet.dto.RegisterRequest;
import com.example.Ewallet.security.JwtHelper;
import com.example.Ewallet.kafka.services.EmailProducerService;
import com.example.Ewallet.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")

public class AuthControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private EmailProducerService emailProducerService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return response;
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password  !!");
        }
    }

    @PostMapping("/register")
    public String createUser(@Valid @RequestBody RegisterRequest user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        PendingUser pendingUser = userService.createPendingUser(user);
        String siteURL = request.getRequestURL().toString();
        String siteURL1 = siteURL.replace(request.getServletPath(), "");
        emailProducerService.sendEmailVerificationMessage(pendingUser, siteURL1);
        return "registration processed";
    }

    @GetMapping("/verify")
    public RedirectView verifyAccount(@Param("code") String code){
        boolean verified = userService.verify(code);
        RedirectView redirectView = new RedirectView();
        if(verified == true) {
            redirectView.setUrl("http://localhost:3000/login/verified");
        }
        else{
            redirectView.setUrl("http://localhost:3000/login/unverified");
        }
        return redirectView;
    }

}

