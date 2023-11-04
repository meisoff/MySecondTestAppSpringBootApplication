package ru.arkhipov.MySecondTestAppSpringBootApplication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.*;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ModifyRequestService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ValidationService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.util.DateTimeUtil;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService, @Qualifier("ModifyOperationUidResponseService") ModifyResponseService modifyResponseService, ModifyRequestService modifyRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("Received request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operatiounUid(request.getOperatiounUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.isCorrectCodeUid(request);
            validationService.isValid(bindingResult);

        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("Validation failed for request: {}", request, e);

            // Log field-level validation errors
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                log.error("Field validation error: Field [{}], Rejected value [{}], Error [{}]",
                        fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("Unsupported code in request: {}", request, e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("Internal server error for request: {}", request, e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        modifyRequestService.modify(request);

        log.info("Sending response: {}", response);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}
