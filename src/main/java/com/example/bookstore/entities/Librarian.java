package com.example.bookstore.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "librarian")
@Table(name = "LIBRARIAN", schema = "public")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private Library library;

    @Column(name = "VERIFIED_ACCOUNT")
    private Boolean verifiedAccount = false;

    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;

    @Column(name = "VERIFICATION_CODE_TIME_EXPIRATION")
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
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
