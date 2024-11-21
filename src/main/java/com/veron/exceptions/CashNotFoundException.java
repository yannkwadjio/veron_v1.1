package com.veron.exceptions;

public class CashNotFoundException extends RuntimeException{
    public CashNotFoundException(String message) {
        super(message);
    }
}
