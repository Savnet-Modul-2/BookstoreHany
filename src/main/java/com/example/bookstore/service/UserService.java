package com.example.bookstore.service;

import com.example.bookstore.entities.User;
import com.example.bookstore.exceptions.AccountNotVerifiedException;
import com.example.bookstore.exceptions.InvalidPasswordException;
import com.example.bookstore.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
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
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException("User with the email address %s already exists".formatted(user.getEmail()));
        }
        String encryptedPassword = DigestUtils
                .md5Hex(user.getPassword()).toUpperCase();
        user.setPassword(encryptedPassword);

        String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(5));

        emailService.sendEmailVerification(user.getEmail(), user.getVerificationCode());
        return userRepository.save(user);
    }

    public User resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = user.getVerificationCodeTimeExpiration();

        if (now.isBefore(expirationTime.minusMinutes(1))) {
            emailService.sendEmailVerification(user.getEmail(), user.getVerificationCode());
            return user;
        }

        String newCode = generateVerificationCode();
        user.setVerificationCode(newCode);
        user.setVerificationCodeTimeExpiration(now.plusMinutes(5));

        emailService.sendEmailVerification(user.getEmail(), newCode);
        return userRepository.save(user);
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(10000, 99999));
    }

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
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

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User verifyAccount(Long userId, String code) {
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

    public User login(String email, String password) throws InvalidPasswordException, AccountNotVerifiedException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email %s not found".formatted(email)));

        if (!user.getVerifiedAccount()) {
            throw new AccountNotVerifiedException("Account not verified.");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return userRepository.save(user);
    }
}