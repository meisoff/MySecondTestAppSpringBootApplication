package ru.arkhipov.MySecondTestAppSpringBootApplication.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    // Идентификатор пользователя
    private String uid;
    // Идентификатор операции
    private String operatiounUid;
    // Время исполнения запроса в системе
    private String systemTime;
    // Успешно/неуспешно обработан запрос
    private Codes code;
    // Премия за год
    private Double annualBonus;
    // Код ошибки
    private ErrorCodes errorCode;
    // Сообщение ошибки
    private ErrorMessages errorMessage;

}
