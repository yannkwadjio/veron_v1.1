package com.veron.exceptions;

public class ProductStockNotFoundException extends RuntimeException{
    public ProductStockNotFoundException(String message) {
        super(message);
    }
}
