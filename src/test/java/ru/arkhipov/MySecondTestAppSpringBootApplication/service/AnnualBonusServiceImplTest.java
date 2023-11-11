package ru.arkhipov.MySecondTestAppSpringBootApplication.service;

import org.junit.jupiter.api.Test;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {

        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus, workDays);

        double expected = 90123.45679012345;
        assertThat(result).isEqualTo(expected);
    }
}