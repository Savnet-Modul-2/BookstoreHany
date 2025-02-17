package com.example.bookstore.service;

import com.example.bookstore.entities.User;
import com.example.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User create(User user) {
        if (user.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new user that you want to create");
        }

        user.setPassword(hashPassword(user.getPassword()));

        emailService.sendEmailVerification(user);
        return userRepository.save(user);
    }

    public User verifyCode(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getVerificationCodeTimeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The verification code has expired.");
        }

        if (!user.getVerificationCode().equals(code)) {
            throw new RuntimeException("Invalid verification code.");
        }

        user.setVerifiedAccount(true);
        return userRepository.save(user);
    }

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateById(Long userId, User userBody) {
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        updatedUser.setFirstName(userBody.getFirstName());
        updatedUser.setLastName(userBody.getLastName());
        updatedUser.setYearOfBirth(userBody.getYearOfBirth());
        updatedUser.setGender(userBody.getGender());
        updatedUser.setEmail(userBody.getEmail());
        updatedUser.setPhoneNumber(userBody.getPhoneNumber());
        updatedUser.setPassword(hashPassword(userBody.getPassword()));
        updatedUser.setCountry(userBody.getCountry());

        return userRepository.save(updatedUser);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return String.format("%032x", new BigInteger(1, digest)).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
