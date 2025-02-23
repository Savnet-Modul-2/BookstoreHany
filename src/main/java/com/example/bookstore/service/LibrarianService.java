package com.example.bookstore.service;

import com.example.bookstore.entities.Librarian;
import com.example.bookstore.entities.Library;
import com.example.bookstore.entities.User;
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
import java.util.Random;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private EmailService emailService;

    public Librarian create(Librarian librarian) {
        if (librarian.getId() != null) {
            throw new EntityExistsException("Librarian with the email address %s already exists".formatted(librarian.getEmail()));
        }

        String sha256Hex = DigestUtils.sha256Hex(librarian.getPassword()).toUpperCase();
        librarian.setPassword(sha256Hex);

        librarian.setVerificationCode(String.valueOf(new Random().nextInt(10000, 99999)));
        librarian.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(5));

        emailService.sendEmailVerification(librarian.getEmail(), librarian.getVerificationCode());

        Library savedLibrary = libraryRepository.save(librarian.getLibrary());
        librarian.setLibrary(savedLibrary);

        return librarianRepository.save(librarian);
    }

    public Librarian verifyAccount(String email, String verificationCode) {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Librarian not found."));

        if (librarian.getVerificationCodeTimeExpiration() != null &&
                LocalDateTime.now().isAfter(librarian.getVerificationCodeTimeExpiration())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code has expired.");
        }

        if (!librarian.getVerificationCode().equals(verificationCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid verification code");
        }

        librarian.setVerifiedAccount(true);
        librarian.setVerificationCode(null);
        librarian.setVerificationCodeTimeExpiration(null);
        return librarianRepository.save(librarian);
    }

    public Librarian login(String email, String password) {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Librarian not found."));
        String sha256Hex = DigestUtils.sha256Hex(password).toUpperCase();
        if (librarian.getPassword().equals(sha256Hex) && librarian.getVerifiedAccount()) {
            return librarian;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password.");
    }
}
