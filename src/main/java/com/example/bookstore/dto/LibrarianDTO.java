package com.example.bookstore.dto;

import com.example.bookstore.validation.BasicValidation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class LibrarianDTO {
    private Long id;
    @NotNull(groups = BasicValidation.class)
    private String firstName;
    @NotNull(groups = BasicValidation.class)
    private String lastName;
    @NotNull(groups = BasicValidation.class)
    private String email;
    @NotNull(groups = BasicValidation.class)
    private String password;
    private LibraryDTO libraryDTO;
    private Boolean verifiedAccount = false;
    private String verificationCode;
    private LocalDateTime verificationCodeTimeExpiration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LibraryDTO getLibraryDTO() {
        return libraryDTO;
    }

    public void setLibraryDTO(LibraryDTO libraryDTO) {
        this.libraryDTO = libraryDTO;
    }

    public Boolean getVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(Boolean verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationCodeTimeExpiration() {
        return verificationCodeTimeExpiration;
    }

    public void setVerificationCodeTimeExpiration(LocalDateTime verificationCodeTimeExpiration) {
        this.verificationCodeTimeExpiration = verificationCodeTimeExpiration;
    }
}
