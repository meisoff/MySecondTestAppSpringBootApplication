package ru.arkhipov.MySecondTestAppSpringBootApplication.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.*;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.AnnualBonusService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ModifyRequestService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.service.ValidationService;
import ru.arkhipov.MySecondTestAppSpringBootApplication.util.DateTimeUtil;
import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(ValidationService validationService, @Qualifier("ModifyOperationUidResponseService") ModifyResponseService modifyResponseService, ModifyRequestService modifyRequestService, AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.annualBonusService = annualBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("Received request: {}", request);

        Response response = createResponse(request);

        try {
            handleValidationAndExceptions(request, bindingResult, response);

        } catch (ValidationFailedException e) {
            return handleException(response, e, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            return handleException(response, e, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return handleException(response, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseAndRequest(response, request);

        log.info("Sending response: {}", response);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }

    private Response createResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operatiounUid(request.getOperatiounUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .annualBonus(annualBonusService.calculate(request.getPositions(), request.getSalary(), request.getBonus(), request.getWorkDays()))
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void handleValidationAndExceptions(Request request, BindingResult bindingResult, Response response) throws ValidationFailedException, UnsupportedCodeException {
        validationService.isCorrectCodeUid(request);
        validationService.isValid(bindingResult);
    }

    private ResponseEntity<Response> handleException(Response response, Exception e, HttpStatus status) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(status == HttpStatus.INTERNAL_SERVER_ERROR ? ErrorCodes.UNKNOWN_EXCEPTION : ErrorCodes.VALIDATION_EXCEPTION);
        response.setErrorMessage(status == HttpStatus.INTERNAL_SERVER_ERROR ? ErrorMessages.UNKNOWN : ErrorMessages.VALIDATION);
        log.error("Error occurred for request: {}", response, e);
        return new ResponseEntity<>(response, status);
    }

    private void modifyResponseAndRequest(Response response, Request request) {
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
    }
}
