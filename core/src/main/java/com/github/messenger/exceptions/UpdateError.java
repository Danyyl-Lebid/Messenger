package com.github.messenger.exceptions;

public class UpdateError extends RuntimeException {

    public UpdateError() {
    }

    public UpdateError(String message) {
        super(message);
    }
}
