package com.university.semanticnetwork.exception;

public class NotFoundException extends RuntimeException{

    private static final String MESSAGE = "Not found exception: ";

    public NotFoundException() {
        super(MESSAGE, null, false, false);
    }

    public NotFoundException(String message) {
        super(MESSAGE + message, null, false, false);
    }
}
