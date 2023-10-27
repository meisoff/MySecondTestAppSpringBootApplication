package ru.arkhipov.MySecondTestAppSpringBootApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Request;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Response;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ValidationService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operatiounUid(request.getOperatiounUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isCorrectCodeUid(request);
            validationService.isValid(bindingResult);

        } catch (ValidationFailedException | UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode(e.getClass().getSimpleName());
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
