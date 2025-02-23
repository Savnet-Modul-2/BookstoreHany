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

    public void sendEmailVerification(String to, String code){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verificare cont aplicatie Bookstore");
        message.setText("Codul tau de verificare este: "+code);
        mailSender.send(message);
    }

}

