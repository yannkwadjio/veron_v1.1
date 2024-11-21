package com.veron.exceptions;

public class CustomerNoFoundException extends RuntimeException{
    public CustomerNoFoundException(String message) {
        super(message);
    }
}
