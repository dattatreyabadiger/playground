package payslip_generator.calculator;

import payslip_generator.dto.Employee;
import payslip_generator.dto.Shift;
import payslip_generator.repository.OvertimePayRepository;

import java.math.BigDecimal;
import java.util.List;

// STRATEGY PATTERN (concrete strategy): computes overtime pay beyond the hours limit.
// Delegates rate/limit lookups to OvertimePayRepository (Repository pattern) via constructor injection.
public class OverPayCalculator implements PayCalculator {

    private final OvertimePayRepository overtimePayRepository;

    public OverPayCalculator(OvertimePayRepository overtimePayRepository) {
        this.overtimePayRepository = overtimePayRepository;
    }

    public BigDecimal calculatePay(Employee employee) {
        List<Shift> shifts = employee.getShifts();
        int workDurationHours = 0;

        for (Shift shift : shifts) {
            workDurationHours += shift.getWorkDuration();
        }

        int overPayHoursLimit = overtimePayRepository.getOvertimeHoursLimit();
        if (workDurationHours <= overPayHoursLimit)
            return BigDecimal.ZERO;

        int totalExtraHours = workDurationHours - overPayHoursLimit;
        BigDecimal overPayPerHour = overtimePayRepository.getOverPayPerHour();
        return overPayPerHour.multiply(BigDecimal.valueOf(totalExtraHours));
    }

}
