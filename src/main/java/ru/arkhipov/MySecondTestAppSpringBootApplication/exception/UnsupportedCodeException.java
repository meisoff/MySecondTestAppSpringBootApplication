package ru.arkhipov.MySecondTestAppSpringBootApplication.exception;

public class UnsupportedCodeException extends Exception {
    public UnsupportedCodeException(String message) {
        super(message);
    }
}