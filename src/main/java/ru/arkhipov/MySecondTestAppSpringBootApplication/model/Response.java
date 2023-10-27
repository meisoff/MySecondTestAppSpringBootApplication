package ru.arkhipov.MySecondTestAppSpringBootApplication.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String uid;
    private String operatiounUid;
    private String systemTime;
    private String code;
    private String errorCode;
    private String errorMessage;

}
