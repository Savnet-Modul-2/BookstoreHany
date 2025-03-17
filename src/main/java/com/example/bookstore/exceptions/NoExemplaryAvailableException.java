package com.example.bookstore.exceptions;

public class NoExemplaryAvailableException extends RuntimeException {
    public NoExemplaryAvailableException(String message) {
        super(message);
    }
}