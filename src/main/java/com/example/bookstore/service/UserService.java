package com.example.bookstore.service;

import com.example.bookstore.entities.User;
import com.example.bookstore.exceptions.AccountNotVerifiedException;
import com.example.bookstore.exceptions.InvalidPasswordException;
import com.example.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User create(User user) {
        String sha256Hex = DigestUtils.sha256Hex(user.getPassword()).toUpperCase();
        user.setPassword(sha256Hex);

        String verificationCode = String.valueOf(new Random().nextInt(100000, 999999));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(10));
        user.setVerifiedAccount(false);

        User savedUser = userRepository.save(user);
        emailService.sendEmailVerification(user.getEmail(), verificationCode);
        return savedUser;
    }

    public User verifyCode(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

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
        updatedUser.setPassword(DigestUtils.md5Hex(userBody.getPassword()).toUpperCase());
        updatedUser.setCountry(userBody.getCountry());

        return userRepository.save(updatedUser);
    }

    public User login(String email, String password) throws InvalidPasswordException, AccountNotVerifiedException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email address %s not found.".formatted(email)));

        if (!user.getVerifiedAccount()) {
            throw new AccountNotVerifiedException("Account not verified.");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return userRepository.save(user);
    }
}