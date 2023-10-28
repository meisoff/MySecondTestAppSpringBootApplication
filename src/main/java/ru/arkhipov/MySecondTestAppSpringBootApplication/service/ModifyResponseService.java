package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
