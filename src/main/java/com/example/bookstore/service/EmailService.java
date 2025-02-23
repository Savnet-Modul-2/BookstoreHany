package com.example.bookstore.service;

import com.example.bookstore.entities.User;
import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmailVerification(User user) {
        user.setVerificationCode(String.valueOf(new Random().nextInt(10000,99999)));
        user.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Verification code for authentication in bookstore app");
        message.setText("Your verification code is: " + user.getVerificationCode());

        mailSender.send(message);
    }

}

