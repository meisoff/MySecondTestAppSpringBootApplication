package ru.arkhipov.MySecondTestAppSpringBootApplication.exception;

public class ValidationFailedException extends Exception {
    public ValidationFailedException(String message) {
        super(message);
    }
}