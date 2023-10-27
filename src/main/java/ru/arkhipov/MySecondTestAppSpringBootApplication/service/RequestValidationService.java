package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Request;

import java.util.stream.Collectors;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            throw new
                    ValidationFailedException((bindingResult.getFieldError().getDefaultMessage()));
        }
    }
    public void isCorrectCodeUid(Request request) throws UnsupportedCodeException {
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("Unsupported uid value: 123");
        }
    }

}
