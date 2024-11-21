package com.veron.exceptions;

public class PayementNotFoundException extends RuntimeException{
    public PayementNotFoundException(String message) {
        super(message);
    }
}
