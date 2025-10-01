package com.labanca.fibonacci.exception;

public class InvalidFibonacciInputException extends RuntimeException {
    public InvalidFibonacciInputException(String message) {
        super(message);
    }
}
