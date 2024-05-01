package com.example.Ewallet.services;

import com.example.Ewallet.collections.PendingUser;
import com.example.Ewallet.collections.User;
import com.example.Ewallet.exceptions.UserAlreadyExistException;
import com.example.Ewallet.dto.RegisterRequest;
import com.example.Ewallet.repositories.PendingUserRepository;
import com.example.Ewallet.repositories.UserRepository;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PendingUserRepository pendingUserRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final String topicName = "email-verification-topic";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public  User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(@Valid PendingUser pendingUser){
        User user = new User();
        user.setUserId(generateUniqueUserId());
        user.setPassword(pendingUser.getPassword());
        user.setRegistrationDate(new Date());
        user.setEmail(pendingUser.getEmail());
        user.setName(pendingUser.getName());
        user.setWalletBalance((double) 0);
        user.setCashback((double) 0);
        user.setIncome((double) 0);
        user.setExpenses((double) 0);
        return userRepository.save(user);
    }

    public boolean verify(String verificationCode){
        PendingUser user = pendingUserRepository.findByToken(verificationCode);
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            User user1 = users.get(i);
            if(user.getEmail().equals(user1.getEmail())){
                return false;
            }
        }
        if(user == null){
            return false;
        }
        else{
            this.createUser(user);
            pendingUserRepository.delete(user);
            return true;
        }
    }

    public PendingUser createPendingUser(RegisterRequest user){
        List<User> users = userRepository.findAll();
        users.forEach(user1 -> {
            if(user.getEmail().equals(user1.getEmail())){
                throw new UserAlreadyExistException("User already exist");
            }
        });
        PendingUser pendingUser = new PendingUser();
        pendingUser.setId(generateUniquePendingUserId());
        pendingUser.setEmail(user.getEmail());
        pendingUser.setName(user.getName());
        pendingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        String randomCode = generateUniqueToken();
        pendingUser.setToken(randomCode);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expirationDate = calendar.getTime();
        pendingUser.setExpirationDate(expirationDate);
        return pendingUserRepository.save(pendingUser);
    }

    private String generateUniqueUserId(){
        String uuid = UUID.randomUUID().toString();
        if(userRepository.existsById(uuid)){
            return generateUniqueUserId();
        }
        return uuid;
    }

    private String generateUniquePendingUserId(){
        String uuid = UUID.randomUUID().toString();
        if(pendingUserRepository.existsById(uuid)){
            return generateUniqueUserId();
        }
        return uuid;
    }

    private String generateUniqueToken(){
        String token = RandomString.make(64);
        if(pendingUserRepository.existsByToken(token)){
            return generateUniqueToken();
        }
        return token;
    }

}
