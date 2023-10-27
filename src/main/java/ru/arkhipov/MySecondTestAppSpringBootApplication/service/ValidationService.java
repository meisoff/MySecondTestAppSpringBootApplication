package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Request;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
    void isCorrectCodeUid(Request request) throws UnsupportedCodeException;
}
