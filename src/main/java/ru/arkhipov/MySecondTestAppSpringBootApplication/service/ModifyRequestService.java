package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Request;

@Service
public interface ModifyRequestService {

    void modify(Request request);
}
