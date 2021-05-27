package com.github.messenger.exceptions;

public class IncorrectPayload extends RuntimeException{

    public IncorrectPayload() {
    }

    public IncorrectPayload(String message) {
        super(message);
    }
}
