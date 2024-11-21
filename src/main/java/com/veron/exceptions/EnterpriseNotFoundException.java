package com.veron.exceptions;

public class EnterpriseNotFoundException extends RuntimeException{
    public EnterpriseNotFoundException(String message) {
        super(message);
    }
}
