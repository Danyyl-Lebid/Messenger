package com.github.messenger.exceptions;

public class Conflict extends RuntimeException {

    public Conflict() {
    }

    public Conflict(String message) {
        super(message);
    }
}
