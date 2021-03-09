package com.university.semanticnetwork.exception;

public class WrongSequenceException extends RuntimeException{

    private static final String MESSAGE = "Wrong data sequence exception: ";

    public WrongSequenceException() {
        super(MESSAGE, null, false, false);
    }

    public WrongSequenceException(String message) {
        super(MESSAGE + message, null, false, false);
    }
}