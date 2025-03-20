package com.example.bookstore.service;

import com.example.bookstore.entities.Librarian;
import com.example.bookstore.entities.Library;
import com.example.bookstore.entities.User;
import com.example.bookstore.exceptions.AccountNotVerifiedException;
import com.example.bookstore.exceptions.InvalidPasswordException;
import com.example.bookstore.mapper.LibrarianMapper;
import com.example.bookstore.mapper.LibraryMapper;
import com.example.bookstore.repository.LibrarianRepository;
import com.example.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private EmailService emailService;

    public Librarian create(Librarian librarian) {
        if (librarianRepository.findByEmail(librarian.getEmail()).isPresent()) {
            throw new EntityExistsException("User with the email address %s already exists".formatted(librarian.getEmail()));
        }
        String encryptedPassword = DigestUtils
                .md5Hex(librarian.getPassword()).toUpperCase();
        librarian.setPassword(encryptedPassword);

        String verificationCode = generateVerificationCode();
        librarian.setVerificationCode(verificationCode);
        librarian.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(5));

        emailService.sendEmailVerification(librarian.getEmail(), librarian.getVerificationCode());
        return librarianRepository.save(librarian);
    }

    public Librarian resendVerificationCode(String email) {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = librarian.getVerificationCodeTimeExpiration();

        if (now.isBefore(expirationTime.minusMinutes(1))) {
            emailService.sendEmailVerification(librarian.getEmail(), librarian.getVerificationCode());
            return librarian;
        }

        String newCode = generateVerificationCode();
        librarian.setVerificationCode(newCode);
        librarian.setVerificationCodeTimeExpiration(now.plusMinutes(5));

        emailService.sendEmailVerification(librarian.getEmail(), newCode);
        return librarianRepository.save(librarian);
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(10000, 99999));
    }

    public Librarian getById(Long librarianId) {
        return librarianRepository.findById(librarianId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }

    public Librarian updateById(Long librarianId, Librarian librarianBody) {
        Librarian updatedLibrarian = librarianRepository.findById(librarianId)
                .orElseThrow(EntityNotFoundException::new);

        updatedLibrarian.setFirstName(librarianBody.getFirstName());
        updatedLibrarian.setLastName(librarianBody.getLastName());
        updatedLibrarian.setEmail(librarianBody.getEmail());
        updatedLibrarian.setPassword(DigestUtils.md5Hex(librarianBody.getPassword()).toUpperCase());
        updatedLibrarian.setLibrary(librarianBody.getLibrary());

        return librarianRepository.save(updatedLibrarian);
    }

    public void deleteById(Long librarianId) {
        librarianRepository.deleteById(librarianId);
    }

    public Librarian verifyAccount(Long librarianId, String code) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        if (librarian.getVerificationCodeTimeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The verification code has expired.");
        }

        if (!librarian.getVerificationCode().equals(code)) {
            throw new RuntimeException("Invalid verification code.");
        }

        librarian.setVerifiedAccount(true);
        return librarianRepository.save(librarian);
    }

    public Librarian login(String email, String password) throws InvalidPasswordException, AccountNotVerifiedException {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with email %s not found".formatted(email)));

        if (!librarian.getVerifiedAccount()) {
            throw new AccountNotVerifiedException("Account not verified.");
        }

        if (!librarian.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return librarianRepository.save(librarian);
    }
}