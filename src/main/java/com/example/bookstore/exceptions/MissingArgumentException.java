package com.example.bookstore.exceptions;

public class MissingArgumentException extends Exception {
    public MissingArgumentException(String message) {
        super(message);
    }
}