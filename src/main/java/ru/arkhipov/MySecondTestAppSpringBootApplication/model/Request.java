package ru.arkhipov.MySecondTestAppSpringBootApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    // Идентификатор пользователя
    @NotBlank(message = "uid is required and must not be blank")
    @Size(max = 32, message = "uid must be up to 32 characters")
    private String uid;

    // Идентификатор операции
    @NotBlank(message = "operatiounUid is required and must not be blank")
    @Size(max = 32, message = "operatiounUid must be up to 32 characters")
    private String operatiounUid;

    // Название системы
    private Systems systemName;

    // Время исполнения запроса в системе
    @NotBlank(message = "systemTime is required and must not be blank")
    private String systemTime;

    // Содержание
    private String source;
    // Позиция сотрудника
    private Positions positions;
    // Зарплата сотрудника
    private Double salary;
    // Бонус коэффициент сотрудника
    private Double bonus;
    // Количество рабочих дней в году
    private int workDays;
    // Идентификатор коммуникации
    @Min(value = 1, message = "communicationId must be at least 1")
    @Max(value = 100000, message = "communicationId must be at most 100000")
    private int communicationId;

    // Номер шаблона
    private int templateId;
    // Код продукта
    private int productCode;
    // Смс код
    private int smsCode;

    // Преобразование в JSON формат
    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", opertionUid='" + operatiounUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}

