package ru.arkhipov.MySecondTestAppSpringBootApplication.service;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBootApplication.model.Positions;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService{

    public int getDaysInCurrentYear() {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 366;
        } else {
            return 365;
        }
    }


    /**
     * @param positions - позиция
     * @param salary - зарпалата
     * @param bonus - бонус коэффициент
     * @param workDays - количество рабочих дней
     * @return зарплата по итогу года
     */
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        return salary * bonus * getDaysInCurrentYear() * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays) {
        double result = 0.00;

        if (positions.isManager()) {
            result = salary * bonus * getDaysInCurrentYear() * positions.getPositionCoefficient() / workDays / 4;
        }
        return result;
    }
}

