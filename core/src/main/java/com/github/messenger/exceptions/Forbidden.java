package com.github.messenger.exceptions;

public class Forbidden extends RuntimeException {

    public Forbidden() {
    }

    public Forbidden(String message) {
        super(message);
    }
}
