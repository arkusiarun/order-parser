package com.parser.orderparser.exception;

/**
 * @author Arun Singh
 * Custom Exception Class
 */

public class ApplicationException extends RuntimeException {

    private final String message;

    public ApplicationException(String message) {
        this.message = message;
    }
}