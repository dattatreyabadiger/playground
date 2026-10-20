package payslip_generator.calculator;

import payslip_generator.dto.Employee;
import payslip_generator.dto.Shift;
import payslip_generator.repository.PayRepository;

import java.math.BigDecimal;
import java.util.List;

// STRATEGY PATTERN (concrete strategy): computes base + skill pay per shift.
// Delegates rate lookups to PayRepository (Repository pattern) via constructor injection.
public class StandardPayCalculator implements PayCalculator {

    private final PayRepository payRepository;

    public StandardPayCalculator(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    @Override
    public BigDecimal calculatePay(Employee employee) {
        List<Shift> shifts = employee.getShifts();

        BigDecimal payForContractPerHour = payRepository.getPayForContract(employee.getContractType());
        BigDecimal totalPay = BigDecimal.ZERO;
        for (Shift shift : shifts) {
            BigDecimal workDuration = BigDecimal.valueOf(shift.getWorkDuration());
            totalPay = totalPay.add(payForContractPerHour.multiply(workDuration));

            BigDecimal payForSkillPerHour = payRepository.getPayForSkill(shift.getSkill());
            totalPay = totalPay.add(payForSkillPerHour.multiply(workDuration));
        }
        return totalPay;
    }
}
