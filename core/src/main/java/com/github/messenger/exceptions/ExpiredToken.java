package com.github.messenger.exceptions;

public class ExpiredToken extends RuntimeException {
    public ExpiredToken() {
    }

    public ExpiredToken(String message) {
        super(message);
    }
}
