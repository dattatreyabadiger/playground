package payslip_generator.calculator;

import payslip_generator.dto.Employee;
import payslip_generator.dto.PayCalculatorType;
import payslip_generator.repository.OvertimePayRepository;
import payslip_generator.repository.PayRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static payslip_generator.dto.PayCalculatorType.OVERTIME_PAY;
import static payslip_generator.dto.PayCalculatorType.STANDARD_PAY;

// FACTORY PATTERN: builds and encapsulates all PayCalculator strategies keyed by PayCalculatorType.
// The map is private - callers never receive raw PayCalculator instances, only go through
// calculatePay()/getPayCalculatorTypes(), keeping strategy creation and selection fully encapsulated.
public class PayCalculatorFactory {

    private final Map<PayCalculatorType, PayCalculator> payCalculators;

    public PayCalculatorFactory(PayRepository payRepository, OvertimePayRepository overtimePayRepository) {
        payCalculators = Map.of(STANDARD_PAY, new StandardPayCalculator(payRepository),
                                OVERTIME_PAY, new OverPayCalculator(overtimePayRepository));
    }

    public BigDecimal calculatePay(PayCalculatorType payCalculatorType, Employee employee) {
        return payCalculators.get(payCalculatorType)
                             .calculatePay(employee);
    }

    public Set<PayCalculatorType> getPayCalculatorTypes() {
        return payCalculators.keySet();
    }
}
