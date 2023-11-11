package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Positions;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
    double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays);
}
