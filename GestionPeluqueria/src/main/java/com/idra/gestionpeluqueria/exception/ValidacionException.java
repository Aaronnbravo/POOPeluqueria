package com.idra.gestionpeluqueria.exception;

public class ValidacionException extends Exception {
    
    public ValidacionException(String message) {
        super(message);
    }
    
    public ValidacionException(String message, Throwable cause) {
        super(message, cause);
    }
}